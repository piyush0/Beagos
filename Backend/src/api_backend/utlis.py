import theano
from keras.models import load_model,Model
import pandas as pd
import numpy as np
import cv2
import requests


model = load_model('/home/vasu/all_projects/Beagos/Backend/ML_Models/CNN_full.h5')
 
def predict_out(file):
	image = cv2.imread(file, 0)
	img = cv2.resize(image, (100,100))
	x = img.reshape(10000)
	X = np.array(x)
	X = X.reshape((1, 100 ,100, 1)) / 255.0
	y = model.predict(X)

	score = y.max()
	score = score*100

	return requests.get('http://127.0.0.1:8000/score/ Score=str(score)')
	


