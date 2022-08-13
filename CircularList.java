// moodle name : rabeeaazreik , ID : 211710124 , name : rabeea azreik
// moodle name : guygavriely , ID : 323832204 , name : guy gavriely
/**
 *
 * Circular list
 *
 * An implementation of a circular list with  key and info
 *
 */

public class CircularList{
    // these are the 4 attributes of this class
    private Item[] array;
    private int start;
    private int length;
    private int maxLen;

    // O(maxLen)
    public CircularList (int maxLen){ // a constructor of the class (start is initialized after the first insert)
        this.array = new Item[maxLen];
        this.maxLen = maxLen;
        this.length = 0;
    }

    /**
     * public Item retrieve(int i)
     *
     * returns the item in the ith position if it exists in the list.
     * otherwise, returns null
     */

    // O(1)
    public Item retrieve(int i)
    {
        if (i < 0 || i >= this.length) { // returns null in case the index is negative or greater than the list's length
            return null;
        }
        return this.array[(i+this.start)%this.maxLen]; // if you got here then the index is legal and it returns array[i]
    }

    /**
     * public int insert(int i, int k, String s)
     *
     * inserts an item to the ith position in list  with key k and  info s.
     * returns -1 if i<0 or i>n  or n=maxLen otherwise return 0.
     */

    // O(min{i+1,n-i+1})
    public int insert(int i, int k, String s) {
        Item item = new Item(k,s); // creates a new Item in order to insert it to the list
        if (i < 0 || i > this.length || this.length == this.maxLen) { // returns -1 in case i<0 or i>length or length = maxLen
            return -1;
        }
        else if (this.length == 0) { // if the list is empty then we initialize start=0 and array[0] = item
            this.start = 0;
            this.array[0] = item;
            this.length++;
            return 0;
        }
        else { // if we got here that mean the list isn't empty and the index is legal
            if (i+1 <= this.length-i+1) { // if this is true that means that it's better to move the values to the left
                if (start > 0) { // if the starting index is not 0
                    for (int j = start-1 ; j < start-1+i ; j++) {
                        this.array[j % this.maxLen] = this.array[(j+1) % maxLen]; // we move the values to the previous index
                    }
                    this.array[(this.start+i-1) % this.maxLen] = item; // we insert the item we wanted to insert
                    this.start--; // because we moved the values to the previous index that means that start also moved to the left
                }
                else { // the case where the starting index is 0
                    if (i == 0) { // when we want to insert to the beginning and start = 0, we just insert it in the last index
                        this.array[this.maxLen - 1] = item;
                        this.start = this.maxLen - 1;
                    }
                    else {
                        this.array[this.maxLen - 1] = this.array[0];
                        this.start = this.maxLen - 1;
                        for (int j = 0 ; j < i-1 ; j++) {
                            this.array[j] = this.array[j+1]; // we move the values to the previous index
                        }
                        this.array[i-1] = item;
                    }
                }
            }
            else { // if we got here that means that it's better to move the values to the right
                for (int j = this.start + this.length ; j > this.start + i ; j--) {
                    this.array[j % this.maxLen] = this.array[(j-1) % this.maxLen]; // we move the values to the next index
                }
                this.array[(this.start + i) % this.maxLen] = item; // we insert the item we wanted to insert
            }
            this.length++;
            return 0;
        }
    }

    /**
     * public int delete(int i)
     *
     * deletes an item in the ith posittion from the list.
     * returns -1 if i<0 or i>n-1 otherwise returns 0.
     */

    // O(min{i+1,n-i+1})
    public int delete(int i)
    {
        if (i < 0 || i > this.length - 1) { // if the index is negative or greater than the list's length return -1
            return -1;
        }
        else if (this.length == 0) { // if the list is empty just returns 0
            return 0;
        }
        else if (i == 0) { // this case is delete first
            this.start = (this.start + 1) % this.maxLen;
            this.length--;
            return 0;
        }
        else if (i == this.length - 1) { // this case is delete last
            this.length--;
            return 0;
        }
        else { // delete an item that isn't the first or last and the list isn't empty
            if (i+1 <= this.length-i+1) { // if this is true that means that it's better for us to move the items to the right
                for (int j = this.start + i ; j > this.start ; j--) {
                    // we move the values to the next index
                    // this might look scary but it's just a trick for negative modulus
                    this.array[((j % this.maxLen) + this.maxLen) % this.maxLen] = this.array[(((j - 1) % this.maxLen) + this.maxLen) % this.maxLen];
                }
                this.start = (this.start + 1) % this.maxLen; // we moved the items to the right so we moved start also to the right
            }
            else { // if we got here than means that its better to move the items to the left
                for (int j = this.start + i ; j < this.start + this.length ; j++) {
                    this.array[(j % this.maxLen)] = this.array[((j+1) % this.maxLen)];
                }
            }
            this.length--;
            return 0;
        }
    }
}