package experiment.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anideshp on 3/18/2016.
 */
public class Person implements Parcelable{

    private String firstName;
    private String lastName;

    private boolean isMinor;


    public enum GENDERS {MALE,FEMALE};

    private GENDERS gender;

    public Person(){
        this("unknwon","unknown",GENDERS.MALE,false);
    }

    public void setIsMinor(boolean isMinor) {
        this.isMinor = isMinor;
    }

    public boolean isMinor() {
        return isMinor;
    }

    public GENDERS getGender(){
        return this.gender;
    }

    public void setGender(GENDERS gender){
        this.gender=gender;
    }


    public Person(String firstName, String lastName, GENDERS gender, boolean isMinor){
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.isMinor=isMinor;
    }

    public Person(Parcel parcel){
        this.firstName=parcel.readString();
        this.lastName=parcel.readString();
        this.gender=(GENDERS)parcel.readSerializable();
        this.isMinor= parcel.readByte()==1?true:false;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeSerializable(this.gender);
        parcel.writeByte((byte)(isMinor==true?1:0));
    }

    public static final Creator<Person> CREATOR=new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        @Override
        public Person[] newArray(int i) {
            return new Person[i];
        }
    };
}
