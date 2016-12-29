from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Details,Score_Return
from .serializer import DetailsSerializers,Score_ReturnSerializers
from utlis import predict_out


@api_view(['GET', 'PUT'])
def detail(request, pk):
 
    try:
        detail = Details.objects.get(pk=pk)
    except Details.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = DetailsSerializers(detail)
        return Response(detail.data)

    elif request.method == 'PUT':
        serializer = DetailsSerializers(detail, data=request.data)
        if detail.is_valid():
            detail.save()
            return predict_out()
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



@api_view(['GET', 'PUT'])
def score_detail(request, pk):

    try:
        score = Score_Return.objects.get(pk=pk)
    except Score_Return.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = Score_ReturnSerializers(score)
        return Response(serializer.data)

    elif request.method == 'PUT':
        serializer = SnippetSerializer(detail, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
