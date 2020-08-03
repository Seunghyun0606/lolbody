from django.urls import path
from . import views

appname = 'analysis'
urlpatterns = [
    path('<str:rank>/<str:lane>/stats/', views.stats),
    path('<str:summoner_name/stats/', views.summoner_stats),
]