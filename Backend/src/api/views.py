from django.shortcuts import render
from api.models import Image,Rating
from api.serializers import ImageS, RatingS
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from utlis import predict_out
import glob

path = '/home/vasu/all_projects/Beagos/Backend/src/media/*'
file = glob.glob(path)   
class ImageD(APIView):

	def get_object(self,pk):
		try:
			return Image.objects.get(pk=pk)
		except Image.DoesNotExist:
			raise Http404

	def get(self, request, pk, format=None):
		image = self.get_object(pk)
		serializer = ImageS(image)
		return Response(serializer.data)

	def put(self, request, pk, format=None):
		image = self.get_object(pk)
		serializer = ImageS(image, data=request.data)
		if serializer.is_valid():
			serializer.save()
			return predict_out(file)
		return Response(serializer.error, status=status.HTTP_400_BAD_REQUEST)
		
	def delete(self, request, pk, format=None):
		image = get_object(pk)
		image.delete()
		return Response(status=status.HTTP_204_NO_CONTENT)


class RatingD(APIView):
	
	def get_object(slef, pk):
		try:
			return Rating.objects.get(pk=pk)
		except:
			raise Http404

	def get(self, request, pk, format=None):
		score = self.get_object(pk)
		serializer = RatingS(score)
		return Response(serializer.data)

	def put(self, request, pk, format=None):
		score = self.get_object(pk)
		serializer = RatingS(score, data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data)
		return Response(serializer.error, status=status.HTTP_400_BAD_REQUEST)	

	def delete(self, request, pk, format=None):
		image = get_object(pk)
		image.delete()
		return Response(status=status.HTTP_204_NO_CONTENT)
		 							