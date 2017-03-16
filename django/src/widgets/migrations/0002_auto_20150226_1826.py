# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('widgets', '0001_initial'),
    ]

    operations = [
        migrations.RenameModel(
            old_name='Amount',
            new_name='WidgetData',
        ),
        migrations.RemoveField(
            model_name='widgetsdata',
            name='amount',
        ),
        migrations.RemoveField(
            model_name='widgetsdata',
            name='price',
        ),
        migrations.DeleteModel(
            name='Price',
        ),
        migrations.DeleteModel(
            name='WidgetsData',
        ),
    ]
