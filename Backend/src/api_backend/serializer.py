from rest_framework import serializers
from .models import Details,Score_Return

class DetailsSerializers(serializers.ModelSerializer):
    class Meta:
        model = Details
        fields = ('id','Image')

class Score_ReturnSerializers(serializers.ModelSerializer):
    class Meta:
        model = Score_Return
        fields = ('id','Score')		