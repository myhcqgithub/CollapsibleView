# collapsible
## Step 1. Add the JitPack repository to your build file
#### Add it in your root build.gradle at the end of repositories:
```java 

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
## Step 2. Add the dependency
```java
dependencies {
	        compile 'com.github.myhcqgithub:CollapsibleView:v0.0.1'
	}
```

## demo map
#### this is default state
![image](https://github.com/myhcqgithub/CollapsibleView/blob/master/img/1.jpg)
#### this is collapse state 
![image](https://github.com/myhcqgithub/CollapsibleView/blob/master/img/2.jpg)
#### this is not expand state 
![image](https://github.com/myhcqgithub/CollapsibleView/blob/master/img/3.jpg)

## expanded attribute
``` java
        <!--right icon-->
        <attr name="right_icon" format="reference"/>
        <!--expand text-->
        <attr name="expand_text" format="string"/>
        <!--collapse text-->
        <attr name="collapse_text" format="string"/>
        <!--text color-->
        <attr name="text_color" format="color|reference"/>
        <!--text size-->
        <attr name="text_size" format="dimension|reference"/>
        <!--is use map animation-->
        <attr name="icon_animation" format="boolean"/>
        <!--is need expand view-->
        <attr name="has_expand_view" format="boolean"/>
        <!--default show count-->
        <attr name="default_show_count" format="integer"/>
        
```        
