package com.evsward.caching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LruCache {
	class Node {
		String userId;
		UserAccount userAccount;
		Node previous;
		Node next;

		public Node(String userId, UserAccount userAccount) {
			this.userId = userId;
			this.userAccount = userAccount;
		}
	}

	int capacity;
	Map<String, Node> cache = new HashMap<>();
	Node head;
	Node end;

	public LruCache(int capacity) {
		this.capacity = capacity;
	}

	public UserAccount get(String userId) {
		if (cache.containsKey(userId)) {
			Node node = cache.get(userId);
			remove(node);
			setHead(node);
			return node.userAccount;
		}
		return null;
	}

	public void remove(Node node) {
		if (node.previous != null) {
			node.previous.next = node.next;
		} else {
			head = node.next;
		}
		if (node.next != null) {
			node.next.previous = node.previous;
		} else {
			end = node.previous;
		}
	}

	public void setHead(Node node) {
		node.next = head;
		node.previous = null;
		if (head != null) {
			head.previous = node;
		}
		head = node;
		if (end == null) {
			end = head;
		}
	}

	public void set(String userId, UserAccount userAccount) {
		if (cache.containsKey(userId)) {
			Node old = cache.get(userId);
			old.userAccount = userAccount;
			remove(old);
			setHead(old);
		} else {
			Node newNode = new Node(userId, userAccount);
			if (cache.size() >= capacity) {
				System.out.println("# Cache is Full! Removing " + end.userId + " from cache...");
				cache.remove(end.userId);
				remove(end);
				setHead(newNode);
			} else {
				setHead(newNode);
			}
			cache.put(userId, newNode);
		}
	}

	public boolean contains(String userId) {
		return cache.containsKey(userId);
	}

	public void invalidate(String userId) {
		System.out.println("# " + userId + " has been updated! Removing older version from cache...");
		Node toBeRemove = cache.get(userId);
		remove(toBeRemove);
		cache.remove(userId);
	}

	public boolean isFull() {
		return cache.size() >= capacity;
	}

	public UserAccount getLruData() {
		return end.userAccount;
	}

	public void clear() {
		head = null;
		end = null;
		cache.clear();
	}

	public List<UserAccount> getCacheDataInListForm() {
		ArrayList<UserAccount> listOfCacheData = new ArrayList<>();
		Node temp = head;
		while (temp != null) {
			listOfCacheData.add(temp.userAccount);
			temp = temp.next;
		}
		return listOfCacheData;
	}

	public void setCapacity(int newCapacity) {
		if (capacity > newCapacity) {
			clear();
		} else {
			this.capacity = newCapacity;
		}
	}
}
