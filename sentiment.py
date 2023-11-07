import streamlit as st
import requests
from textblob import TextBlob
from bs4 import BeautifulSoup
import spacy

# Set your API key
api_key = '08580393d090448282a4532bbaa1ce1c'

# Load spaCy model
nlp = spacy.load("en_core_web_sm")

# Function to obtain news articles
def obtenir_articles_presse(ticker):
    url = f'https://newsapi.org/v2/everything?q={ticker}&apiKey={api_key}'
    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        if 'articles' in data:
            articles = [article['url'] for article in data['articles']]
            return articles
        else:
            return None
    else:
        st.error(f"Erreur lors de la récupération des articles de presse. Code d'erreur : {response.status_code}")
        return None

# Function to obtain article content
def obtenir_contenu_article(url):
    response = requests.get(url)
    if response.status_code == 200:
        soup = BeautifulSoup(response.text, 'lxml')
        article_content = ' '.join([p.text for p in soup.find_all('p')])
        return article_content
    else:
        return None

# Function to analyze sentiment
def analyse_sentiments(text):
    doc = nlp(text)
    blob = TextBlob(text)
    sentiment = blob.sentiment
    return sentiment

# Streamlit app
st.title("Analyse de sentiment des articles de presse")

ticker = st.text_input("Entrez le ticker de l'action (par exemple, AAPL):")

if st.button("Obtenir les articles de presse"):
    articles = obtenir_articles_presse(ticker)

    if articles:
        st.success(f"Articles de presse liés à {ticker} obtenus avec succès.")
        st.write("Analyse des sentiments :")

        for article_url in articles:
            article_content = obtenir_contenu_article(article_url)
            if article_content:
                sentiments = analyse_sentiments(article_content)
                st.subheader("Article URL :")
                st.write(article_url)
                st.write("Score de sentiment (polarité) :", sentiments.polarity)
                st.write("Score de subjectivité :", sentiments.subjectivity)
                st.write("\n")
            else:
                st.warning(f"Impossible d'obtenir le contenu de l'article à partir de l'URL : {article_url}")
    else:
        st.warning("Impossible d'obtenir des articles de presse.")
