import pymongo
from pymongo import MongoClient
import timeit
import time
import numpy
import os
import pandas as pd
import gensim
from gensim.corpora import Dictionary
from gensim.models.ldamulticore import LdaMulticore
from gensim.models.coherencemodel import CoherenceModel
from gensim.parsing.preprocessing import STOPWORDS as SW
from sklearn.decomposition import LatentDirichletAllocation, TruncatedSVD
from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer
from sklearn.model_selection import GridSearchCV
from pprint import pprint
from gensim.utils import simple_preprocess
from gensim.parsing.preprocessing import STOPWORDS
import numpy as np

np.random.seed(2018)
import nltk
from nltk import PorterStemmer
from nltk.stem import WordNetLemmatizer, SnowballStemmer
from nltk.stem.porter import *
nltk.download('stopwords')
from nltk.corpus import stopwords
nltk.download('wordnet')
import pyLDAvis
import pyLDAvis.gensim_models
import csv
from csv import writer
from csv import reader
import io
import re

def lemmatize_stemming(text):
    return  nltk.wordnet.WordNetLemmatizer().lemmatize(text) #PorterStemmer().stem(WordNetLemmatizer().lemmatize(text, pos='v'))

def preprocess(text):
    text = re.sub(r'https://\S+', '', text)
    result = []
    for token in gensim.utils.simple_preprocess(text):
        if token not in gensim.parsing.preprocessing.STOPWORDS and len(token) > 3:
            result.append(lemmatize_stemming(token))
    return result

def run_my_program():
    client = MongoClient("mongodb://127.0.0.1:27017")
    db=client.pitman
    datacol = db["feedData"]
    mongo_docs = datacol.find()
    fieldnames = list(mongo_docs[0].keys())

    output_file = os.path.join('rawData.csv')
        
    with io.open(output_file, 'w', newline='', encoding="utf-8") as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames, extrasaction="ignore")
            writer.writeheader()
            writer.writerows(mongo_docs)
    
    data = pd.read_csv('rawData.csv');
    data_text = data[['message']]
    size = len(data)
    print("size: {}".format(size))
    data_text['index'] = data_text.index
    documents = data_text
    print(len(documents))
    print(documents[:10])
    doc_sample = documents[documents['index'] == 1].values[0][0]
    print('original document: ')
    words = []
    for word in doc_sample.split(' '):
        words.append(word)
    print(words)
    print('\n\n tokenized and lemmatized document: ')
    preprocessed_words = preprocess(doc_sample)
    print("preprocessed words")
    print(preprocessed_words)
    string_preprocessed_words = ""
    for x in preprocessed_words:
        string_preprocessed_words = string_preprocessed_words+x+" "
    print("String preprocessed words")
    print(string_preprocessed_words)
    
    processed_docs = documents['message'].map(preprocess)
    string_processed_docs = []
    for x in processed_docs:
        str = ""
        for z in x:
            str = str + z + " "

        string_processed_docs.append(str)

    with io.open('rawData.csv', 'r', encoding="utf8") as read_obj, io.open('procData.csv', 'w', newline='', encoding="utf8") as write_obj:
        # Create a csv.reader object from the input file object
        csv_reader = csv.reader(read_obj)
        # Create a csv.writer object from the output file object
        csv_writer = csv.writer(write_obj)
        # Read each row of the input csv file as list
        i=-1
        for row in csv_reader:
            # Append the default text in the row / list
            if(i==-1):
                row.append("clean_text")
                i=i+1
            else:
                row.append(string_processed_docs[i])
                i=i+1
            # Add the updated row / list to the output file
            csv_writer.writerow(row)
    data = pd.read_csv('procData.csv');
    data_clean_text = data[['clean_text']]

    id2word = gensim.corpora.Dictionary(processed_docs)
    count = 0
    for k, v in id2word.iteritems():
        print(k, v)
        count += 1

    # Creating a corpus object 
    corpus = [id2word.doc2bow(d) for d in processed_docs]
    # Instantiating a Base LDA model 
    base_model = LdaMulticore(corpus=corpus, num_topics=5, id2word=id2word, workers=12, passes=5)
    # Filtering for words 
    words = [re.findall(r'"([^"]*)"',t[1]) for t in base_model.print_topics()]
    # Create Topics
    topics = [' '.join(t[0:10]) for t in words]
    # Getting the topics
    for id, t in enumerate(topics): 
        print(f"------ Topic {id} ------")
        print(t, end="\n\n")
    # Compute Perplexity
    # a measure of how good the model is. lower the better
    base_perplexity = base_model.log_perplexity(corpus)
    print('\nPerplexity: ', base_perplexity) 
    # Compute Coherence Score
    coherence_model = CoherenceModel(model=base_model, texts=processed_docs,dictionary=id2word, coherence='c_v')
    coherence_lda_model_base = coherence_model.get_coherence()
    print('\nCoherence Score: ', coherence_lda_model_base)
    #Creating Topic Distance Visualization 
    #pyLDAvis.enable_notebook()
    from sklearn.feature_extraction.text import CountVectorizer
    # the vectorizer object will be used to transform text to vector form
    vectorizer = CountVectorizer(max_df=100, min_df=0.9, token_pattern='\w+|\$[\d\.]+|\S+')
    # apply transformation
    tf = vectorizer.fit_transform(data_clean_text) #.toarray()
    # tf_feature_names tells us what word each column in the matric represents
    tf_feature_names = vectorizer.get_feature_names()
    tf.shape # --> (200000, 2296)


    number_of_topics = size
    model = LatentDirichletAllocation(n_components=number_of_topics, random_state=45) # random state for reproducibility
    # Fit data to model
    model.fit(tf)
    
    #Defining a function to loop over number of topics to be used to find an 
    #optimal number of tipics
    def compute_coherence_values(dictionary, corpus, texts, limit, start=2, step=3):
        """
        Compute c_v coherence for various number of topics

        Parameters:
        ----------
        dictionary : Gensim dictionary
        corpus : Gensim corpus
        texts : List of input texts
        limit : Max num of topics

        Returns:
        -------
        model_list : List of LDA topic models
        coherence_values : Coherence values corresponding to the 
        LDA model with respective number of topics
        """
        coherence_values_topic = []
        model_list_topic = []
        for num_topics in range(start, limit, step):
            model = LdaMulticore(corpus=corpus, num_topics=num_topics, id2word=id2word)
            model_list_topic.append(model)
            coherencemodel = CoherenceModel(model=model, texts=texts, dictionary=dictionary, coherence='c_v')
            coherence_values_topic.append(coherencemodel.get_coherence())

        return model_list_topic, coherence_values_topic
    start = timeit.timeit()
    strt = time.time()
    # takes a long time to run.
    model_list_topic, coherence_values_topic = compute_coherence_values(dictionary=id2word,
                                                            corpus=corpus,
                                                            texts=processed_docs,
                                                            start=2, limit=200, step=6)
    end = timeit.timeit()
    nd = time.time()
    print(end-start)
    print(nd-strt)
    print("took this much time to finish this function")
    print("done")
    print("---------model_list_topic-----------")
    print(model_list_topic)
    print("---------coherence_values_topic-----------")
    print(coherence_values_topic)
    max_coherence = numpy.max(coherence_values_topic)
    max_coherence_index = coherence_values_topic.index(max_coherence)
    print("max coherence value and index are:")
    print(max_coherence)
    print(max_coherence_index)
    best_lda_model = model_list_topic[max_coherence_index]
    best_lda_model = LdaMulticore(corpus = corpus,
                                  num_topics = size,
                                  id2word=id2word,
                                  chunksize=2000,
                                  passes=25,
                                  decay=0.5,
                                  iterations=70)

    topics = []
    for idx, topic in best_lda_model.print_topics(-1):
        print('Topic: {} \nWords: {}'.format(idx, topic))
        same = False
        for x in topics:
            if(topic == x):
                same = True
        if same == False:
            topics.append(topic)

    client = MongoClient("mongodb://127.0.0.1:27017")
    db=client.pitman
    topicsdata = db["topics"]
    feeddata = db["feedData"]
    topicsdata.delete_many({})
    for x in topics:
        wordstemp = re.findall(r'[a-z]+', x);
        probabilitiestemp = re.findall(r'\d+\.\d+', x)
        words = []
        probabilities = []
        words.append(wordstemp[0])
        probabilities.append(probabilitiestemp[0])
        done = False
        i=1
        while done == False and i < len(wordstemp):
            if probabilitiestemp[i] == probabilitiestemp[i-1]:
                words.append(wordstemp[i])
                probabilities.append(probabilitiestemp[i])
                i=i+1
            else:
                done = True

        for z in feeddata.find({}, {"_id": 0}):
            found = True
            for w in words:
                str = z['message'].lower()
                if w in str:
                    continue
                else:
                    found = False
                    break
            if found == True:
                topicsdata.insert_one(z)
                topicsdata.update_one( {"message": z['message']}, { "$set" : {"topic": words, "probability": probabilities}})

    topicsdata.update_many({}, { "$rename" : {"user_id": "userId", "created_time": "createdTime",
                           "message_sentiment": "messageSentiment", "replies_sentiment": "repliesSentiment"}})