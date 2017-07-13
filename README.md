# CollapsibleView
# A retractable layout, such as listview, is used
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

## demo mp4
![](https://github.com/myhcqgithub/CollapsibleView/blob/master/img/4.mp4)
## usage method 
```java
  //setAdapter
  mDefaultAdapter = new DefaultAdapter<String>(list, R.layout.item) {
            @Override
            public void bind(DefaultHolder defaultHolder, int position, String item) {
                defaultHolder.setText(R.id.text, item);
            }
        };
   mRoot.setAdapter(mDefaultAdapter);
  //set listener 
   mRoot.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(CollapsibleLayout view, int position, Object data) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    mRoot.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(CollapsibleLayout view, int position, Object data) {
                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    mRoot.setToggleListener(new ToggleListener() {
            @Override
            public void toggle(CollapsibleLayout layout, boolean toggle) {
                Toast.makeText(MainActivity.this, toggle + "", Toast.LENGTH_SHORT).show();
            }
        });
	
   //refresh
   mDefaultAdapter.notifyDataSetChanged();
```	
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
