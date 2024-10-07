
## Cloud Computing Documentation

### Step by Step Deployment

1. **Clone the GitHub Repository**

   Clone the Yap2Type repository to your local machine using the following command:

   A. Clone Repositori Utama

   ```bash
      git clone https://github.com/rahmanfaisal0414/C241-PS042-Capstone-Yap2Type.git
   ```
   
   B. Move to Subdirectory, After cloning the main repository, move to the subdirectory you want:

   ```bash
   cd C241-PS042-Capstone-Yap2Type/cc
   ```

2. **Download `tf_model.h5` In Drive**
   Download the `tf_model.h5` file from the provided Google Drive link or any other source where it's hosted.

   ```bash
   https://drive.google.com/drive/folders/1JJXHK9qaq_EVuX9VGWGQt1v4a5VtBJQj?usp=sharing

3. **Save tf_model.h5 to directory model**
   Save the downloaded tf_model.h5 file into the model directory within the cloned repository. 

4. **Install Required Libraries**
   You can follow the docker, requirements , dockerignore file libraries that we use

## API Speech to Text

In Yap2Type, the speech-to-text functionality is powered by a cloud computing service. Here is a step-by-step explanation of how the integration is achieved:

1. **Service Account Creation**:
   - First, a service account is created with the necessary permissions. The role chosen for the service account is `Service Account Admin`. This role allows managing and configuring the service account which will be used to authenticate and authorize API requests.

2. **Enable Speech-to-Text API**:
   - After creating the service account, the Speech-to-Text API is enabled. This involves going to the API services in the cloud platform and enabling the Speech-to-Text API.

3. **Authentication and API Usage**:
   - With the service account and the API enabled, an authentication token is generated which is used to authenticate API requests. The application then sends audio files to the API, which processes them and returns the transcribed text.

By leveraging cloud computing, Yap2Type can efficiently handle speech-to-text conversion, providing accurate and fast transcription services without requiring extensive local processing power.

## Deployment Requirements

Below is the `requirements.txt` file listing the necessary Python packages for Yap2Type:

```plaintext
Flask==3.0.3
flask-cors==4.0.1
tensorflow==2.16.1
tf-keras==2.16.0
transformers==4.41.2
Werkzeug==3.0.3
blinker==1.8.2
```

## Docker Configuration

### Docker Ignore File

Below is the `.dockerignore` file to exclude unnecessary files from the Docker build context:

```plaintext
__pycache__
*.pyc
*.pyo
*.pyd
.Python
env
build
dist
*.egg-info
```

### DOCKERFILE

```plaintext
FROM python:3.9-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install sentencepiece \
    && pip install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 8080

ENV PORT 8080

CMD ["python", "main.py"]
```

## Deployed Cloud Run API Endpoint
To run the Yap2Type cloud run API with the `/summarize` endpoint, use the following URL:

```plaintext
https://summarizeapi-7c7o3mtyua-et.a.run.app/summarize
```


## Index Endpoints
**Base URL :**

> `/summarize`

**Method :**

> `POST`

## Netral Classification
#### Netral EndPoint : <br>
header key: 

> `content-type value:app/json`

body :
> `raw:json`

Example Teks :
> `{
    "text": "Dua orang tewas dan dua lainnya mengalami luka berat akibat ditusuk pria inisial AN saat malam takbiran di Gunungsindur, Bogor, Jawa Barat. Sebelum kejadian, pelaku dan korban sempat berpesta miras.Pelaku, korban, dan saksi lainnya sekitar 15 orang berkumpul untuk merayakan malam takbiran, kata Kasat Reskrim Polres Bogor AKP Teguh Kumara dalam keterangannya, Selasa (18/6/2024).Dalam acara tersebut, Zaki (korban tewas) membawa minuman keras sebanyak empat botol. Lalu sebagian karyawan minum minuman keras tersebut, termasuk AN (tersangka), Zaki, Ulul (korban luka berat), Edo, dan Andi alias Keweh, sambungnya.Baca artikel detiknews, Pria Tusuk 4 Orang Saat Malam Takbiran di Bogor Sempat Pesta Miras selengkapnya https://news.detik.com/berita/d-7396601/pria-tusuk-4-orang-saat-malam-takbiran-di-bogor-sempat-pesta-miras."
}`

Summary :
> `{
    "summary": "Dua orang tewas dan dua lainnya mengalami luka berat akibat ditusuk pria inisial AN saat malam takbiran di Gunungsindur, Bogor, Jawa Barat. Sebelum kejadian, pelaku dan korban sempat berpesta miras.Pelaku, korban, dan saksi lainnya sekitar 15 orang berkumpul untuk merayakan malam takbiran, kata Kasat Reskrim Polres Bogor AKP Teguh Kumara.Dalam acara tersebut, Zaki membawa minuman keras sebanyak empat botol."
}`
