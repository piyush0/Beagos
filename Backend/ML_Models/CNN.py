import numpy as np
from matplotlib import pyplot as plt
import theano
from keras.layers import Convolution2D, MaxPooling2D, Dense, Flatten, Reshape, AveragePooling2D, Dropout,Input,Activation
from keras.models import Sequential,Model
from keras.utils import np_utils

X = np.load('./logos_data_X.npy')
Y = np.load('./logos_data_Y.npy')
X[2245] = X[2249]

x_low, x_high = 70,110 
X_train = X[:40,:]
Y_train = Y[:40,]
for _ in range(31):
    X_train = np.concatenate((X_train, X[x_low:x_high, :]), axis=0)
    Y_train = np.concatenate((Y_train, Y[x_low:x_high,]), axis=0)
    x_low = x_low + 70
    x_high = x_high + 70
    
X_train = np.concatenate((X_train, X[2240:2248,:]), axis=0)
Y_train = np.concatenate((Y_train, Y[2240:2248,]), axis=0)
X_train = np.concatenate((X_train, X[2500:3500,:]), axis=0)
Y_train = np.concatenate((Y_train, Y[2500:3500,]), axis=0)

x_low, x_high = 110,140 
X_crossval = X[40:70,:]
Y_crossval = Y[40:70,]
for _ in range(31):
    X_crossval = np.concatenate((X_crossval, X[x_low:x_high, :]), axis=0)
    Y_crossval = np.concatenate((Y_crossval, Y[x_low:x_high,]), axis=0)
    x_low = x_low + 70
    x_high = x_high + 70

X_crossval = np.concatenate((X_crossval, X[2248:2250,:]), axis=0)
Y_crossval = np.concatenate((Y_crossval, Y[2248:2250,]), axis=0)    
X_crossval = np.concatenate((X_crossval, X[2250:2500,:]), axis=0)
Y_crossval = np.concatenate((Y_crossval, Y[2250:2500,]), axis=0)
X_crossval = np.concatenate((X_crossval, X[3500:,:]), axis=0)
Y_crossval = np.concatenate((Y_crossval, Y[3500:,]), axis=0)

X_train = X_train.reshape((X_train.shape[0], 100 ,100, 1)) / 255.0
X_crossval = X_crossval.reshape((X_crossval.shape[0], 100 ,100, 1)) / 255.0

model = Sequential()

inp = Input(shape=(100, 100, 1))

c1 = Convolution2D(64, 3, 3, activation='relu')(inp)
c2 = Convolution2D(32, 3, 3, activation='relu')(c1)
m1 = MaxPooling2D(pool_size=(2, 2))(c2)
c3 = Convolution2D(32, 3, 3, activation='relu')(m1)
c4 = Convolution2D(16, 3, 3, activation='relu')(c3)
m2 = MaxPooling2D(pool_size=(2, 2))(c4)
c4 = Convolution2D(8, 3, 3, activation='relu')(m2)
f1 = Flatten()(c4)
fc1 = Dense(128)(f1) 
a1 = Activation('relu')(fc1)
d1 =  Dropout(0.35)(a1)
fc2 = Dense(34)(d1) 
a2 = Activation('softmax')(fc2)

model = Model(input=inp, output=a2)
temp = Model(input=inp, output=fc2)


model.summary()
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

y_tr = np_utils.to_categorical(Y_train)
y_cr = np_utils.to_categorical(Y_crossval)

hist = model.fit(X_train, y_tr,
                nb_epoch=19,
                shuffle=True,
                batch_size=128,
                validation_data=(X_crossval, y_cr))

model.save('CNN_full.h5')
temp.save('CNN_temp.h5')
