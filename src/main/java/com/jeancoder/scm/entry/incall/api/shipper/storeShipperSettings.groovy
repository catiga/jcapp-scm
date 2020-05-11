package com.jeancoder.scm.entry.incall.api.shipper

import com.jeancoder.app.sdk.JC
import com.jeancoder.scm.ready.incall.api.ProtObj

def id = JC.request.param('id');
def autoDelivery = JC.request.param('autoDelivery');
def Name = JC.request.param('Name');
def Tel = JC.request.param('Tel');
def ProvinceName = JC.request.param('ProvinceName');
def CityName = JC.request.param('CityName');
def ExpAreaName = JC.request.param('ExpAreaName');
def Address = JC.request.param('Address');
def discovery_img_height = JC.request.param('discovery_img_height');
def discovery_img = JC.request.param('discovery_img');
def goods_id = JC.request.param('goods_id');

def city_id = JC.request.param('city_id');
def province_id = JC.request.param('province_id');
def district_id = JC.request.param('district_id');

def countdown = JC.request.param('countdown');
def reset = JC.request.param('reset');


return ProtObj.success("");
