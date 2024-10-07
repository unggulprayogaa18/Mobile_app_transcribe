package com.rahman.yap2type.view.Home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rahman.yap2type.R
import com.rahman.yap2type.data.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class SummaryActivity : BottomSheetDialogFragment() {

    private lateinit var closeButton: ImageButton
    private lateinit var summarizeButton: Button
    private lateinit var summaryText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var copyTextButton: Button

    private var originalText: String = ""
    private var summarizedText: String = ""

    companion object {
        private const val ARG_SUMMARY_TEXT = "summaryText"

        fun newInstance(summaryText: String): SummaryActivity {
            val fragment = SummaryActivity()
            val args = Bundle()
            args.putString(ARG_SUMMARY_TEXT, summaryText)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton = view.findViewById(R.id.closeButton)
        summarizeButton = view.findViewById(R.id.summarizeButton)
        summaryText = view.findViewById(R.id.summaryText)
        progressBar = view.findViewById(R.id.progressBar)
        copyTextButton = view.findViewById(R.id.copyTextButton)

        closeButton.setOnClickListener {
            dismiss()
        }

        arguments?.getString(ARG_SUMMARY_TEXT)?.let { summary ->
            originalText = summary
            summaryText.setText(originalText)
        }

        summarizeButton.setOnClickListener {
            val textToSummarize = summaryText.text.toString()
            if (textToSummarize.isNotEmpty()) {
                if (checkWordCount(textToSummarize)) {
                    summarizeText(textToSummarize)
                } else {
                    Toast.makeText(requireContext(), "Text should have at least 300 words.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Text should be non-empty.", Toast.LENGTH_SHORT).show()
            }
        }

        copyTextButton.setOnClickListener {
            copySummarizedTextToClipboard()
        }
    }

    private fun checkWordCount(text: String): Boolean {
        val words = text.split("\\s+".toRegex())
        return words.size <= 300
    }

    private fun summarizeText(text: String) {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            summaryText.visibility = View.GONE
            summarizeButton.isEnabled = false
            try {
                SendDataTask(object : SendDataTask.ResponseListener {
                    override fun onTaskCompleted(response: String) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            try {
                                val jsonResponse = JSONObject(response)
                                summarizedText = jsonResponse.getString("summary")
                                summaryText.setText(summarizedText)
                                summarizeButton.text = "Re-Summarize"
                                copyTextButton.visibility = View.VISIBLE
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(requireContext(), "Failed to parse summary: ${e.message}", Toast.LENGTH_LONG).show()
                                summaryText.setText(originalText)
                            }
                            progressBar.visibility = View.GONE
                            summaryText.visibility = View.VISIBLE
                            summarizeButton.isEnabled = true
                        }
                    }

                    override fun onTaskFailed(errorMessage: String) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Failed to summarize text: $errorMessage", Toast.LENGTH_LONG).show()
                            summaryText.setText(originalText)
                            progressBar.visibility = View.GONE
                            summaryText.visibility = View.VISIBLE
                            summarizeButton.isEnabled = true
                        }
                    }
                }).execute(RetrofitClient.BASE_URL + "summarize", "{\"text\":\"$text\"}")
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to summarize text: ${e.message}", Toast.LENGTH_LONG).show()
                summaryText.setText(originalText)
                progressBar.visibility = View.GONE
                summaryText.visibility = View.VISIBLE
                summarizeButton.isEnabled = true
            }
        }
    }

    private fun copySummarizedTextToClipboard() {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Summarized Text", summarizedText)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if (summaryText.text.toString() != originalText) {
            summaryText.setText(originalText)
        }
    }

    class SendDataTask(private val listener: ResponseListener) {

        interface ResponseListener {
            fun onTaskCompleted(response: String)
            fun onTaskFailed(errorMessage: String)
        }

        fun execute(url: String, dataToSend: String) {
            kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
                var conn: HttpURLConnection? = null
                try {
                    conn = URL(url).openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/json")
                    conn.doOutput = true

                    val os = OutputStreamWriter(conn.outputStream, "UTF-8")
                    os.write(dataToSend)
                    os.flush()

                    if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                        val br = conn.inputStream.bufferedReader()
                        val response = br.use { it.readText() }
                        br.close()
                        listener.onTaskCompleted(response)
                    } else {
                        val errorMessage = "Error: ${conn.responseCode} ${conn.responseMessage}"
                        listener.onTaskFailed(errorMessage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    val errorMessage = "Error: ${e.message}"
                    listener.onTaskFailed(errorMessage)
                } finally {
                    conn?.disconnect()
                }
            }
        }
    }
}
