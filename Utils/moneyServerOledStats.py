import time
from luma.core.interface.serial import i2c
from luma.core.render import canvas
from luma.oled.device import sh1106
import subprocess
import numpy
oled = sh1106(i2c(port=1,address=0x3c))

def i2cOLEDstatsView(ResponseData,title,coords):
    with canvas(oled) as draw:
        for index in range(len(title)):
            draw.text((coords[index][0],coords[index][1]),title[index]+' : '+ResponseData[index],fill="red")
        cputemps = "vcgencmd measure_temp"
        result = subprocess.run(cputemps, capture_output=True, text=True,shell=True)
        draw.text((0,45),"system temp: "+str(result.stdout).strip('temp='),fill="red")
def dataUpdater(commands):
    ans = []
    for cmdd in commands:
        result = subprocess.run(cmdd, capture_output=True, text=True, shell=True)
        lines = result.stdout.strip().split('\n')
        array_2d = [line.split() for line in lines]
        arraydata = numpy.array(array_2d,dtype="object")
        data1 = "inactive"
        for i in arraydata:
            index = 0
            for j in i:
                if(index==1 and str(j)=='active'):
                    data1 = j
                if(index==0 and str(j)=='Active:'):
                    index+=1
                else:
                    break
        ans.append(data1)
    return ans

while True:
    command = []
    title = []
    coords = []

    title.append("tomcat10")
    coords.append([0,0])
    command.append("systemctl status tomcat10")
    
    title.append("backupService")
    coords.append([0,15])
    command.append("systemctl status moneyServerDBbackuptask.timer")
    
    title.append("postgresql")
    coords.append([0,30])
    command.append("systemctl status postgresql")

    ResponseData = dataUpdater(command)
    i2cOLEDstatsView(ResponseData,title,coords)
    time.sleep(15)
