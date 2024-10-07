from flask import Flask, request, jsonify
from flask_cors import CORS
from transformers import TFT5ForConditionalGeneration, T5Tokenizer
import tensorflow as tf
import os

app = Flask(__name__)
CORS(app)

model = TFT5ForConditionalGeneration.from_pretrained("model")
tokenizer = T5Tokenizer.from_pretrained('tokenizer')

@app.route('/summarize', methods=['POST'])
def summarize():
    content = request.json
    text = content['text']

    inputs = tokenizer.encode("summarize: " + text, return_tensors="tf", max_length=512, truncation=True)

    summary_ids = model.generate(inputs, max_length=150, min_length=40, length_penalty=2.0, num_beams=4, early_stopping=True)
    summary = tokenizer.decode(summary_ids[0], skip_special_tokens=True)

    return jsonify({'summary': summary})

if __name__ == '__main__':
    port = int(os.environ.get('PORT', 8080))
    app.run(host='0.0.0.0', port=port)