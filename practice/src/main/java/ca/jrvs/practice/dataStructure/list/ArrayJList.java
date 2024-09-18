package ca.jrvs.practice.dataStructure.list;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;

import java.util.Arrays;
import java.util.Objects;

public class ArrayJList<E> implements JList<E> {

  /**
   * Default initial capacity.
   */
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * The array buffer into which the elements of the ArrayList are stored.
   * The capacity of the ArrayList is the length of this array buffer. Any
   * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
   * will be expanded to DEFAULT_CAPACITY when the first element is added.
   */
  transient Object[] elementData; // non-private to simplify nested class access

  /**
   * The size of the ArrayList (the number of elements it contains).
   *
   * @serial
   */
  private int size;

  /**
   * Constructs an empty list with the specified initial capacity.
   *
   * @param  initialCapacity  the initial capacity of the list
   * @throws IllegalArgumentException if the specified initial capacity
   *         is negative
   */
  public ArrayJList(int initialCapacity) {
    if (initialCapacity > 0) {
      this.elementData = new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Illegal Capacity: "+
          initialCapacity);
    }
  }

  /**
   * Constructs an empty list with an initial capacity of ten.
   */
  public ArrayJList() {
    this.elementData = new Object[DEFAULT_CAPACITY];
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
  }

  /**
   *
   * @return number of elements in this list
   */
  @Override
  public int size() {
    return size;
  }

  /**
   *
   * @return <tt>true</tt> if list is empty
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }


  /**
   *
   * @param o element whose presence in this list is to be tested
   * @return <tt>true</tt> if index of o is larger than 0, meaning that it exists within list
   */
  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  @Override
  public E get(int index) {
    return null;
  }

  /**
   *
   * @param e element to be appended to this list
   * @return <tt>true</tt> if object is added successfully
   */
  @Override
  public boolean add(E e) {
    add(e, elementData, size);
    return true;
  }

  /**
   *
   * @param e element to be appended to list
   * @param elementData
   * @param size
   */
  private void add(E e, Object[] elementData, int size) {

  }

  /**
   *
   * @param o element whose presence in this list is to be tested
   * @return i = index of the first occurrence of 0, if o exists within list,
   * else it returns -1
   */
  @Override
  public int indexOf(Object o) {
    return indexOfRange(o, 0, size);
  }

  private int indexOfRange(Object o, int start, int end) {
    if (o == null) {
      for (int i = start; i < end; i++) {
        if (elementData[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = start; i < end; i++) {
        if (o.equals(elementData[i])) {
          return i;
        }
      }
    }
    return -1;
  }

  public E set(int index, E element) {
//    E oldValue = elementData(index);
    elementData[index] = element;
    return element;
//    return oldValue;
  }

//  E elementData(int index) {
//    return elementData(index);
//  }

//  @Override
//  public E get(int index) {
//   return set(index,  elementData[index]);
//  }

  @Override
  public E remove(int index) {
    return null;
  }

  @Override
  public void clear() {

  }
}
