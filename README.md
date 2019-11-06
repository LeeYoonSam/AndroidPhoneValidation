# AndroidPhoneValidation

## Feature
1. displayed dialcode + Flag
2. phone field and check validation
3. provide attributes for change widget resource

## Example
```xml
<com.ys.phoneinfo.view.DialCodeEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:validText="It's valid"
        app:inValidText="It's invalid"
        app:validTextColor="#d5d5d5"
        app:inValidTextColor="#ff0000"
        app:spinnerBg="@drawable/selector_spinner"
        app:editBg="@drawable/selector_edit"/>
```

### option
1. set Valid/Invalid Text Message
2. set Valid/Invalid Text Color
3. set Spinner background drawable
4. set EditText background drawable


## Using Library
https://github.com/google/libphonenumber
