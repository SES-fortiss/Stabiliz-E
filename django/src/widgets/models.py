from django.db import models
from django.utils.encoding import smart_unicode

from decimal import Decimal

# Create your models here.
    
# DataTable for all Widgets
class WidgetData(models.Model):    
    #date of published data -  only display data with most recent date
    timestamp = models.DateTimeField(auto_now_add=True, auto_now=False)
    
    #data for module 16 -Amount of Consumed Energy
    amount_co2 = models.CharField(max_length=5, null=True, blank=False)
    amount_own = models.CharField(max_length=5, null=True, blank=False)
    
    #data for module 21 - Energy Price
    price_today = models.DecimalField(max_digits=5, decimal_places=2,default=Decimal('0.00'))
    price_tomorrow = models.DecimalField(max_digits=5, decimal_places=2,default=Decimal('0.00'))
    
    #data for module 15 - Energy Saver of the Week/Month
    energy_saver_of_the_week_first_name = models.CharField(max_length=30,default="Mr.")
    energy_saver_of_the_week_last_name = models.CharField(max_length=30,default="NoNe")
    energy_saver_of_the_month_first_name = models.CharField(max_length=30,default="Mrs.")
    energy_saver_of_the_month_last_name = models.CharField(max_length=30,default="NoNe")
    
    #data for module 20 - Battery
    battery_charging = models.BooleanField(default=False)
    battery_used = models.BooleanField(default=False)
    battery_stored = models.IntegerField(max_length=6, default=0)
    battery_loaded = models.IntegerField(max_length=3, default=0)
    
    #data for module 14 - Financial Relation
    laptop = models.IntegerField(max_length=2, default=0)
    
    #data for module 19 - Natural Methaphor and Trees needed for Compensation
    earth = models.IntegerField(max_length=2, default=0)
    trees = models.IntegerField(max_length=2, default=0)
    
    #data for module 13 - Yesterday's Consumption
    low_consumption = models.IntegerField(max_length=2, default=0)
    high_consumption = models.IntegerField(max_length=2, default=0)
    yest_consumption = models.IntegerField(max_length=2, default=0)
    trend = models.IntegerField(max_length=2, default=0)
    streetlight = models.IntegerField(max_length=2, default=0)
    
    #identifier for entry
    def __unicode__(self): #__str__ on Python 3
        return smart_unicode(self.timestamp)

#data for module 22 - Imformations/Hints on energy Saving
class Info(models.Model):
    #foreign key for reference to overall data
    amount = models.ForeignKey(WidgetData)
    #particular information
    info_text = models.CharField(max_length=2000,default="Information about energy saving")
    
    #identifier for entry
    def __unicode__(self): #__str__ on Python 3
        return smart_unicode(self.info_text)
