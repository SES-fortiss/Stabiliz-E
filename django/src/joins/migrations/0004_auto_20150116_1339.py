# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('joins', '0003_auto_20150114_1112'),
    ]

    operations = [
        migrations.AlterField(
            model_name='userprofile',
            name='website',
            field=models.URLField(blank=True),
        ),
    ]
