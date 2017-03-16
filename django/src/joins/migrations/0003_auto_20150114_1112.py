# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('joins', '0002_userprofile'),
    ]

    operations = [
        migrations.AlterField(
            model_name='userprofile',
            name='website',
            field=models.CharField(max_length=30),
        ),
    ]
