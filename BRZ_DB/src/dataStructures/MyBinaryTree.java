package dataStructures;

import java.util.ArrayList;

public class MyBinaryTree<E extends Comparable<E>> 
{
	TreeNode<E> root;

	public MyBinaryTree() {
		root = null;    
	}
	
	public boolean isEmpty() {        
		return root == null;       
	}
	
	public void add(E e)
	{
		if (root == null)
			root = new TreeNode<E>(e);
		else 
		{
			TreeNode<E> parent = root;
			TreeNode<E> current = root;
			
			while (current != null) 
			{
				if(e.compareTo(current.value) <= 0) // duplicates go to the left of the node
				{
					parent = current;
					current = parent.left;
				}
				else if(e.compareTo(current.value) > 0)
				{
					parent = current;
					current = parent.right;
				}
			}
			
			if(parent != null)
			{
				if(e.compareTo(parent.value) <= 0)
					parent.left = new TreeNode<E>(e);
				else 
					parent.right = new TreeNode<E>(e);
			}
		}
	}
	
	public ArrayList<E> find(E e)
	{
		ArrayList<E> itemsFound = new ArrayList<E>();
		TreeNode<E> current = root;
		
		while (current != null)
		{
			if (e.compareTo(current.value) < 0)
				current = current.left;
			else if (e.compareTo(current.value) > 0)
				current = current.right;
			else // found
			{
				itemsFound.add(current.value);
				
				// if the element on the left is also the item searched, continue the loop
				if(current.left != null && e.compareTo(current.left.value) == 0)
					current = current.left;
				else 
					break;
			}
		}
		
		return itemsFound;
	}
}
