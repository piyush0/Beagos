from __future__ import unicode_literals

from django.db import models

class Details(models.Model):
	Image = models.ImageField(blank=True)

	def __init__(self):
		return None

class Score_Return(models.Model):
	Score = models.FloatField()

	def __init__(self):
		return None