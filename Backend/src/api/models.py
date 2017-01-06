from __future__ import unicode_literals
from django.db import models
import os

class Image(models.Model):
	image = models.ImageField(upload_to = 'media')

class Rating(models.Model):
	score = models.FloatField(default = 0.0)