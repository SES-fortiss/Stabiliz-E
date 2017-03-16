# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from decimal import Decimal


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Amount',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('amount_co2', models.CharField(max_length=5, null=True)),
                ('amount_own', models.CharField(max_length=5, null=True)),
                ('timestamp', models.DateTimeField(auto_now_add=True)),
                ('price_today', models.DecimalField(default=Decimal('0.00'), max_digits=5, decimal_places=2)),
                ('price_tomorrow', models.DecimalField(default=Decimal('0.00'), max_digits=5, decimal_places=2)),
                ('energy_saver_of_the_week_first_name', models.CharField(default=b'Mr.', max_length=30)),
                ('energy_saver_of_the_week_last_name', models.CharField(default=b'NoNe', max_length=30)),
                ('energy_saver_of_the_month_first_name', models.CharField(default=b'Mrs.', max_length=30)),
                ('energy_saver_of_the_month_last_name', models.CharField(default=b'NoNe', max_length=30)),
                ('battery_charging', models.BooleanField(default=False)),
                ('battery_used', models.BooleanField(default=False)),
                ('battery_stored', models.IntegerField(default=0, max_length=6)),
                ('battery_loaded', models.IntegerField(default=0, max_length=3)),
                ('laptop', models.IntegerField(default=0, max_length=2)),
                ('earth', models.IntegerField(default=0, max_length=2)),
                ('trees', models.IntegerField(default=0, max_length=2)),
                ('low_consumption', models.IntegerField(default=0, max_length=2)),
                ('high_consumption', models.IntegerField(default=0, max_length=2)),
                ('yest_consumption', models.IntegerField(default=0, max_length=2)),
                ('trend', models.IntegerField(default=0, max_length=2)),
                ('streetlight', models.IntegerField(default=0, max_length=2)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Info',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('info_text', models.CharField(default=b'Information about energy saving', max_length=2000)),
                ('amount', models.ForeignKey(to='widgets.Amount')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Price',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('price_today', models.DecimalField(default=Decimal('0.00'), max_digits=5, decimal_places=2)),
                ('price_tomorrow', models.DecimalField(default=Decimal('0.00'), max_digits=5, decimal_places=2)),
                ('timestamp', models.DateTimeField(auto_now_add=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='SignUp',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('first_name', models.CharField(max_length=120, null=True, blank=True)),
                ('last_name', models.CharField(max_length=120, null=True, blank=True)),
                ('email', models.EmailField(max_length=75)),
                ('timestamp', models.DateTimeField(auto_now_add=True)),
                ('updated', models.DateTimeField(auto_now=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='WidgetsData',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('timestamp', models.DateTimeField(auto_now_add=True)),
                ('amount', models.ForeignKey(to='widgets.Amount')),
                ('price', models.ForeignKey(to='widgets.Price')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
