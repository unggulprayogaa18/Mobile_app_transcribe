FROM python:3.9-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install sentencepiece \
    && pip install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 8080

ENV PORT 8080

CMD ["python", "main.py"]
