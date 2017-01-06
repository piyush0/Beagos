from rest_framework import serializers
from api.models import Image,Rating

class ImageS(serializers.ModelSerializer):
	
	class Meta:
		model = Image
		fields = ('id','image')

class RatingS(serializers.ModelSerializer):
	
	class Meta:
		model = Rating
		fields = ('id','score')		