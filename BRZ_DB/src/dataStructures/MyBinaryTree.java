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
				if(e.compareTo(current.value) < 0)
				{
					parent = current;
					current = parent.left;
				}
				else if(e.compareTo(current.value) > 0)
				{
					parent = current;
					current = parent.right;
				}
				else //if the element already exists, do not add 
				{
					parent = null;
					break;
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
	
	public ArrayList<TreeNode<E>> find(E e)
	{
		ArrayList<TreeNode<E>> path = new ArrayList<TreeNode<E>>();
		TreeNode<E> current = root;
		path.add(current);
		
		while (current != null)
		{
			if (e.compareTo(current.value) < 0)
				current = current.left;
			else if (e.compareTo(current.value) > 0)
				current = current.right;
			else // if found, break the loop
				break;
				
			path.add(current);
		}
		
		return path;
	}
	
	public String toString() 
	{
		StringBuilder nodes = new StringBuilder();

		TreeNode<E> node = root;
	    int level = 0;
		nodes = printNode(nodes, node, level, "root");
		
		return nodes.toString(); 
	}
	
	public StringBuilder printNode(StringBuilder nodes, TreeNode<E> node, int level, String direction)
	{
	    if (node != null)
	    {
	    	for (int i = 0; i < level; i++)
	    		nodes.append("  ");
	    	
	    	nodes.append(level + "." + direction + ". " + node.value.toString() + "\n");
	    	
	    	if (node.left != null)
	    	{
	    		printNode(nodes, node.left, ++level, "left");
	    		level--;
	    	}
	    	if (node.right != null)
	    	{
	    		printNode(nodes, node.right, ++level, "right");
	    		level--;
	    	}
	    }
	    
		return nodes;
	}
}
