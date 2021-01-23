/*
 * Copyright © 2020-2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photowey.expirable.cache.boot.util;

import java.util.*;

/**
 * {@code CollectionUtils}
 *
 * @author photowey
 * @date 2021/01/23
 * @since 1.0.0
 */
public final class CollectionUtils {

    private CollectionUtils() {
        // utils class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * Is empty .
     *
     * @param collection the collection
     * @return the boolean
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Is not empty.
     *
     * @param collection the collection
     * @return the boolean
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Is empty.
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isEmpty(Object[] array) {
        return !isNotEmpty(array);
    }

    /**
     * Is not empty.
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }

    public static class CollectionFactory {

        public <E> Collection<E> create(final Class<?> collectionType, final int capacity) {
            return create(collectionType, null, capacity);
        }

        public <E> Collection<E> create(final Class<?> collectionType, final Class<?> elementType, final int capacity) {
            if (collectionType.isInterface()) {
                if (Set.class == collectionType || Collection.class == collectionType) {
                    return new LinkedHashSet<>(capacity);
                } else if (List.class == collectionType) {
                    return new ArrayList<>(capacity);
                } else if (SortedSet.class == collectionType || NavigableSet.class == collectionType) {
                    return new TreeSet<>();
                } else {
                    throw new IllegalArgumentException("Unsupported Collection interface: " + collectionType.getName());
                }
            } else if (EnumSet.class == collectionType) {
                return (Collection<E>) EnumSet.noneOf(asEnumType(elementType));
            } else {
                if (!Collection.class.isAssignableFrom(collectionType)) {
                    throw new IllegalArgumentException("Unsupported Collection type: " + collectionType.getName());
                }
                try {
                    return (Collection<E>) collectionType.newInstance();
                } catch (Throwable ex) {
                    throw new IllegalArgumentException("Could not instantiate Collection type: " + collectionType.getName(), ex);
                }
            }
        }

        public <K, V> Map<K, V> createMap(final Class<?> mapType, final int capacity) {
            return createMap(mapType, null, capacity);
        }

        <K, V> Map<K, V> createMap(final Class<?> mapType, final Class<?> keyType, final int size) {
            if (mapType.isInterface()) {
                if (Map.class == mapType) {
                    return new LinkedHashMap<>(size);
                } else if (SortedMap.class == mapType || NavigableMap.class == mapType) {
                    return new TreeMap<>();
                } else {
                    throw new IllegalArgumentException("Unsupported Map interface: " + mapType.getName());
                }
            } else if (EnumMap.class == mapType) {
                return new EnumMap(asEnumType(keyType));
            } else {
                if (!Map.class.isAssignableFrom(mapType)) {
                    throw new IllegalArgumentException("Unsupported Map type: " + mapType.getName());
                }
                try {
                    return (Map<K, V>) mapType.newInstance();
                } catch (Throwable ex) {
                    throw new IllegalArgumentException("Could not instantiate Map type: " + mapType.getName(), ex);
                }
            }
        }

        private Class<? extends Enum> asEnumType(final Class<?> enumType) {
            if (!Enum.class.isAssignableFrom(enumType)) {
                throw new IllegalArgumentException("Supplied type is not an enum: " + enumType.getName());
            }
            return enumType.asSubclass(Enum.class);
        }
    }
}
