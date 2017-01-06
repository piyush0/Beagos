import cv2
import glob
import numpy as np
from matplotlib import pyplot as plt
import glob
import os

masks = os.listdir('/home/vasu/Hackathon_logos/FlickrLogos-v2/classes/jpg//')X=[]
for string in masks:
    l1 = os.listdir(pth + 'jpg/' + string + '/')
    l2 = os.listdir(pth + 'masks/' + string + '/')
    for ix in range(70):
    	image,text = l1[ix], l2[l2.index(l1[ix] + '.bboxes.txt')]
        img = cv2.imread(pth + 'jpg/' + string + '/' + image, 0)
        dt = open(pth + 'masks/' + string + '/' + text).read()
        dt = str(dt)
        x, y, w, h = dt.split()[4:8]
        x, y, w,h = int(x), int(y), int(w), int(h)
        cropped = img[y:y+h, x:x+w]
        img = cv2.resize(cropped, (100,100))
        x = img.reshape(10000)
        X.append(x)  

path = '/home/vasu/Hackathon_logos/coding_ninjas/*.png'   
files=glob.glob(path)   
for file in files:
    image = cv2.imread(file, 0)
    img = cv2.resize(image, (100,100))
    x = img.reshape(10000)
    X.append(x)

path = '/home/vasu/Hackathon_logos/FlickrLogos-v2/classes/no-logo/*.jpg'   
files=glob.glob(path)   
count = 0
for file in files:
    while count < 1750 :
        image = cv2.imread(file, 0)
        img = cv2.resize(image, (100,100))
        x = img.reshape(10000)
        X.append(x)
        count = count+1
	X_train = np.array(X)

y= []
count = 1
for x in range(32):
    for z in range(70):
        y.append(x+1)

for x in range(10):
    y.append(33)

for x in range(1750):
    y.append(0)
Y_train = np.array(y)

np.save('logos_data_X', X_train)
np.save('logos_data_Y', Y_train)
