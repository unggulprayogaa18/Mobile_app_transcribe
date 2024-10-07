package com.rahman.yap2type.view.About

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahman.yap2type.R

class MemberAdapter(private val members: List<Member>) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.imageMember.setImageResource(member.imageResourceId)
        holder.textName.text = member.name
        holder.textEmail.text = member.email

        if (position < members.size - 1) {
            holder.divider.visibility = View.VISIBLE
        } else {
            holder.divider.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return members.size
    }

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageMember: ImageView = itemView.findViewById(R.id.imageMember)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textEmail: TextView = itemView.findViewById(R.id.textEmail)
        val divider: View = itemView.findViewById(R.id.divider)
    }
}
