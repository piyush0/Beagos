from django.conf.urls import url
from django.contrib import admin
from rest_framework.urlpatterns import format_suffix_patterns
from api.views import ImageD, RatingD

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^image/(?P<pk>[0-9]+)/$', ImageD.as_view()),
    url(r'^score/(?P<pk>[0-9]+)/$', RatingD.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)