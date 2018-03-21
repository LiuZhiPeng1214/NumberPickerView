[![](https://jitpack.io/v/LiuZhiPeng1214/NumberPickerView.svg)](https://jitpack.io/#LiuZhiPeng1214/NumberPickerView)
# Android 数字加减选择器

## 使用方法

### 引用
  Step 1. Add it in your root build.gradle at the end of repositories:
```js
  
    
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
 Step 2. Add the dependency:
 ``` js
 dependencies {
	        compile 'com.github.LiuZhiPeng1214:NumberPickerView:v1.0.0-beta'
	}
 ```
 ### 使用方法
 
 #### 支持自定义属性

``` js

<resources>
    <declare-styleable name="NumberButton">
        <attr name="editable" format="boolean"/> //是否可以输入
        <attr name="buttonWidth" format="dimension" />  // 按钮宽度
        <attr name="editextWidth" format="dimension" />  // 输入框宽度
        <attr name="textSize" format="dimension"/> // 字体大小
        <attr name="textColor" format="color"/> // 字体颜色

    </declare-styleable>
</resources>
```
#### xml 定义

``` js
 <com.eme.numberpickerlibrary.NumberPickerView
        android:id="@+id/npv_number"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp"
        android:layout_marginTop="8dp"
        app:buttonWidth="30dp"
        app:editable="true"
        app:editextWidth="30dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:textColor="#f00"
        app:textSize="14sp"/>

```

#### 使用方法

``` js
 NumberPickerView npvnumber = (NumberPickerView) findViewById(R.id.npv_number);
        npvnumber.setMaxValue(60)
                .setCurrentInventory(50)
                .setMinDefaultNum(0)
                .setCurrentNum(0)
                .setmOnClickInputListener(new NumberPickerView.OnClickInputListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                        Toast.makeText(MainActivity.this,"超过最大库存",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningMinInput(int minValue) {
                        Toast.makeText(MainActivity.this,"低于最小设定值",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWarningMaxInput(int maxValue) {
                        Toast.makeText(MainActivity.this,"超过最大限制量",Toast.LENGTH_SHORT).show();
                    }
                });

```
