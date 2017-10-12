/*
 *      BoundedLinkedList.java
 *      
 *      Copyright 2013 Artur Augustyniak <artur@aaugustyniak.pl>
 *      
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */
package pl.aaugustyniak.simplefilter.utils;

import java.util.LinkedList;

/**
 *
 * @author Artur Augustyniak
 */
public class BoundedLinkedList<E> extends LinkedList<E> {

    private Integer boundSize;

    public BoundedLinkedList(Integer size) {
        super();
        this.boundSize = size;
    }

    @Override
    public void addFirst(E e) {
        if (this.size() == this.boundSize) {
            this.removeLast();
        }
        super.addFirst(e);
    }
}
