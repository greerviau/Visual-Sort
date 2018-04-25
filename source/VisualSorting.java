import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VisualSorting {
	//FRAME
	private JFrame jf;
	//GENERAL VARIABLES
	private int len = 50;
	private int off = 0;
	private int curAlg = 0;
	private int spd = 15;
	private int compare = 0;
	private int acc = 0;
	//GRAPH VARIABLES
	private final int SIZE = 600;
	private int current = -1;
	private int check = -1;
	private int width = SIZE/len;
	private int type = 0;
	//ARRAYS
	private int[] list;
	private String[] types = {"Bar Graph", "Dot Plot"};
	private String[] algorithms = {"Selection Sort", 
								   "Bubble Sort",
								   "Cocktail Sort",
								   "Odd Even Sort", 
								   "Insertion Sort", 
								   "Tim Sort", 
								   "Quick Sort", 
								   "Heap Sort", 
								   "Merge Sort", 
								   "Pigeonhole Sort", 
								   "Radix Sort(MSB)",
								   "Bogo Sort"};
	private String[] algInfo = {"Best Case: O(n^2)\nWorst Case: O(n^2)\nAverage: O(n^2)",
								"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)",
								"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)",
								"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)",
								"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)",
								"Best Case: O(n)\nWorst Case: O(nlogn)\nAverage: O(nlogn)",
								"Best Case: O(nlogn)\nWorst Case: O(n^2)\nAverage: O(nlogn)",
								"Best Case: O(nlogn)\nWorst Case: O(nlogn)\nAverage: O(nlogn)",
								"Best Case: O(nlogn)\nWorst Case: O(nlogn)\nAverage: O(nlogn)",
								"Best Case: --\nWorst Case: O(n+2^k)\nAverage: O(n+2^k))",
								"Best Case: --\nWorst Case: O(n*(k/d))\nAverage: O(n*(k/d))",
								"Best Case: O(n)\nWorst Case: O(n*n!)\nAverage: O(oo)\n\nSee you after the heat death \nof the Uniiverse."};
	//BOOLEANS
	private boolean sorting = false;
	private boolean shuffled = true;
	//UTIL OBJECTS
	SortingAlgorithms algorithm = new SortingAlgorithms();
	Random r = new Random();
	//PANELS
	JPanel tools = new JPanel();
	GraphCanvas canvas;
	//LABELS
	JLabel delayL = new JLabel("Delay :");
	JLabel msL = new JLabel(spd+" ms");
	JLabel sizeL = new JLabel("Size :");
	JLabel lenL = new JLabel(len+"");
	JLabel compareL = new JLabel("Comparisons : " + compare);
	JLabel accessL = new JLabel("Array Accesses : " + acc);
	JLabel algorithmL = new JLabel("Algorithms");
	JLabel typeL = new JLabel("Graph Types");
	//DROP DOWN BOX
	JComboBox alg = new JComboBox(algorithms);
	JComboBox graph = new JComboBox(types);
	JTextArea information = new JTextArea(algInfo[curAlg]);
	//BUTTONS
	JButton sort = new JButton("Sort");
	JButton shuffle = new JButton("Shuffle");
	JButton credit = new JButton("Credit");
	//SLIDERS
	JSlider size = new JSlider(JSlider.HORIZONTAL,1,6,1);
	JSlider speed = new JSlider(JSlider.HORIZONTAL,0,100,spd);
	//BORDER STYLE
	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	//CONSTRUCTOR
	public VisualSorting() {
		shuffleList();	//CREATE THE LIST
		initialize();	//INITIALIZE THE GUI
	}
	
	public void createList() {
		list = new int[len];	//CREATES A LIST EQUAL TO THE LENGTH
		for(int i = 0; i < len; i++) {	//FILLS THE LIST FROM 1-LEN
			list[i] = i+1;
		} 
	}
	
	public void shuffleList() {
		createList();
		for(int a = 0; a < 500; a++) {	//SHUFFLE RUNS 500 TIMES
			for(int i = 0; i < len; i++) {	//ACCESS EACH ELEMENT OF THE LIST
				int rand = r.nextInt(len);	//PICK A RANDOM NUM FROM 0-LEN
				int temp = list[i];			//SETS TEMP INT TO CURRENT ELEMENT
				list[i] = list[rand];		//SWAPS THE CURRENT INDEX WITH RANDOM INDEX
				list[rand] = temp;			//SETS THE RANDOM INDEX TO THE TEMP
			}
		}
		sorting = false;
		shuffled = true;
	}
	
	public void initialize() {
		//SET UP FRAME
		jf = new JFrame();
		jf.setSize(800,635);
		jf.setTitle("Visual Sort");
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.getContentPane().setLayout(null);
		
		//SET UP TOOLBAR
		tools.setLayout(null);
		tools.setBounds(5,10,180,590);
		tools.setBorder(BorderFactory.createTitledBorder(loweredetched,"Controls"));
		
		//SET UP ALGORITHM LABEL
		algorithmL.setHorizontalAlignment(JLabel.CENTER);
		algorithmL.setBounds(40,20,100,25);
		tools.add(algorithmL);
		
		//SET UP DROP DOWN
		alg.setBounds(30,45,120,25);
		tools.add(alg);

		//SET UP GRAPH TYPE LABEL
		typeL.setHorizontalAlignment(JLabel.CENTER);
		typeL.setBounds(40,80,100,25);
		tools.add(typeL);
		
		//SET UP GRAPH TYPE DROP DOWN
		graph.setBounds(30,105,120,25);
		tools.add(graph);
		
		//SET UP SORT BUTTON
		sort.setBounds(40,150,100,25);
		tools.add(sort);
		
		//SET UP SHUFFLE BUTTON
		shuffle.setBounds(40,190,100,25);
		tools.add(shuffle);
		
		//SET UP DELAY LABEL
		delayL.setHorizontalAlignment(JLabel.LEFT);
		delayL.setBounds(10,230,50,25);
		tools.add(delayL);
		
		//SET UP MS LABEL
		msL.setHorizontalAlignment(JLabel.LEFT);
		msL.setBounds(135,230,50,25);
		tools.add(msL);
		
		//SET UP SPEED SLIDER
		speed.setMajorTickSpacing(5);
		speed.setBounds(55,230,75,25);
		speed.setPaintTicks(false);
		tools.add(speed);
		
		//SET UP SIZE LABEL
		sizeL.setHorizontalAlignment(JLabel.LEFT);
		sizeL.setBounds(10,275,50,25);
		tools.add(sizeL);
		
		//SET UP LEN LABEL
		lenL.setHorizontalAlignment(JLabel.LEFT);
		lenL.setBounds(140,275,50,25);
		tools.add(lenL);
		
		//SET UP SIZE SLIDER
		size.setMajorTickSpacing(50);
		size.setBounds(55,275,75,25);
		size.setPaintTicks(false);
		tools.add(size);
		
		//SET UP COMPARISONS LABEL
		compareL.setHorizontalAlignment(JLabel.LEFT);
		compareL.setBounds(10,320,200,25);
		tools.add(compareL);
		
		//SET UP ARRAY ACCESS LABEL
		accessL.setHorizontalAlignment(JLabel.LEFT);
		accessL.setBounds(10,360,200,25);
		tools.add(accessL);
		
		//SET UP INFO AREA
		information.setBounds(10,400,160,125);
		information.setEditable(false);
		tools.add(information);

		//SET UP CREDIT BUTTON
		credit.setBounds(40,540,100,25);
		tools.add(credit);
		
		//SET UP CANVAS FOR GRAPH
		canvas = new GraphCanvas();
		canvas.setBounds(190,0,SIZE,SIZE);
		canvas.setBorder(BorderFactory.createLineBorder(Color.black));
		jf.getContentPane().add(tools);
		jf.getContentPane().add(canvas);

		//ADD ACTION LISTENERS
		alg.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				curAlg = alg.getSelectedIndex();
				information.setText(algInfo[curAlg]);
			}
			
		});
		graph.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//canvas.setType(graph.getSelectedIndex());
				type = graph.getSelectedIndex();
				shuffleList();
				reset();
				Update();
			}
		});
		sort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(shuffled) {
					sorting = true;
					compare = 0;
					acc = 0;
				}
				
			}
		});
		shuffle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shuffleList();
				reset();
			}
		});
		speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				spd = (int)speed.getValue();
				msL.setText(spd+" ms");
			}
		});
		size.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int val = size.getValue();
				if(size.getValue() == 5)
					val = 4;
				len = val * 50;
				lenL.setText(len+"");
				if(list.length != len)
					shuffleList();
				reset();
			}
			
		});
		credit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jf, "	                         Visual Sort\n"
												+ "             Copyright (c) 2017-2018\n"
												+ "                         Greer Viau\n"
												+ "           Build Date:  March 1, 2018   ", "Credit", JOptionPane.PLAIN_MESSAGE, new ImageIcon(""));
			}
		});
		
		sorting();
	}
	
	//SORTING STATE
	public void sorting() {
		if(sorting) {
			try {
				switch(curAlg) {	//CURRENT ALGORITHM IS EXECUTED
					case 0:
						algorithm.selectionSort();
						break;
					case 1:
						algorithm.bubbleSort();
						break;
					case 2:
						algorithm.cocktailSort();
						break;
					case 3:
						algorithm.oddEvenSort();
						break;
					case 4:
						algorithm.insertionSort(0, len-1);
						break;
					case 5:
						algorithm.timSort(len);
						break;
					case 6:
						algorithm.quickSort(0,len-1);
						break;
					case 7:
						algorithm.heapSort();
						break;
					case 8:
						algorithm.mergeSort(0,len-1);
						break;
					case 9:
						algorithm.pigeonholeSort();
						break;
					case 10:
						algorithm.radixSort(len);
						break;
					default:
						algorithm.bogoSort();
						break;
				}
			} catch(IndexOutOfBoundsException e) {}	//EXCEPTION HANDLER INCASE LIST ACCESS IS OUT OF BOUNDS
		}
		reset();	//RESET
		pause();	//GO INTO PAUSE STATE
	}
	
	//PAUSE STATE
	public void pause() {
		int i = 0;
		while(!sorting) {	//LOOP RUNS UNTIL SORTING STARTS
			i++;
			if(i > 100)
				i = 0;
			try {
				Thread.sleep(1);
			} catch(Exception e) {}
		}
		sorting();	//EXIT THE LOOP AND START SORTING
	}
	
	//RESET SOME VARIABLES
	public void reset() {
		sorting = false;
		current = -1;
		check = -1;
		off = 0;
		Update();
	}
	
	//DELAY METHOD
	public void delay() {
		try {
			Thread.sleep(spd);
		} catch(Exception e) {}
	}
	
	//UPDATES THE GRAPH AND LABELS
	public void Update() {	
		width = SIZE/len;
		canvas.repaint();
		compareL.setText("Comparisons : " + compare);
		accessL.setText("Array Accesses : " + acc);
	}
	
	//MAIN METHOD
	public static void main(String[] args) {
		new VisualSorting();
	}

	//SUB CLASS TO CONTROL THE GRAPH
	class GraphCanvas extends JPanel {
		
		public GraphCanvas() {
			setBackground(Color.black);
		}
		
		//PAINTS THE GRAPH
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int i = 0; i < len; i++) {	//RUNS TROUGH EACH ELEMENT OF THE LIST
				int HEIGHT = list[i]*width;	//SETS THE HEIGHT OF THE ELEMENT ON THE GRAPH
				if(type == 0) {		//BAR GRAPH TYPE
					g.setColor(Color.white);	//DEFAULT COLOR
					if(current > -1 && i == current) {
						g.setColor(Color.green);	//COLOR OF CURRENT
					}
					if(check > -1 && i == check) {
						g.setColor(Color.red);	//COLOR OF CHECKING
					}
					//DRAWS THE BAR AND THE BLACK OUTLINE
					g.fillRect(i*width, SIZE-HEIGHT, width, HEIGHT);
					g.setColor(Color.black);
					g.drawRect(i*width, SIZE-HEIGHT, width, HEIGHT);
				}
				else if(type == 1) {	//DOT PLOT TYPE
					g.setColor(Color.white);	//DEFAULT COLOR
					if(current > -1 && i == current) {
						g.setColor(Color.green);	//COLOR OF CURRENT
					}
					if(check > -1 && i == check) {
						g.setColor(Color.red);	//COLOR OF CHECKING
					}
					//DRAWS THE DOT
					g.fillOval(i*width,SIZE-HEIGHT,width,width);
				}
			}
		}
	}
	
	//SUB CLASS FOR ALGORITHMS
	class SortingAlgorithms {
		
		public void selectionSort() {
			int c = 0;
			while(c < len && sorting) {
				int sm = c;
				current = c;
				for(int i = c+1; i < len; i++) {
					if(!sorting)
						break;
					if(list[i] < list[sm]) {
						sm = i;
					}
					check = i;
					compare++;	acc+=2;
					Update();
					delay();
				}
				if(c != sm)
					swap(c,sm);
				c++;
			}
		}
		
		public void insertionSort(int start, int end) {
			for(int i = start+1; i <= end; i++) {
				current = i;
				int j = i;
				while(list[j] < list[j-1] && sorting) {
					swap(j,j-1);
					check = j;
					compare++;	acc+=2;
					Update();
					delay();
					if(j > start+1)
						j--;
				}
			}
		}
		
		public void bubbleSort() {
			int c = 1;
			boolean noswaps = false;
			while(!noswaps && sorting) {
				current = len-c;
				noswaps = true;
				for(int i = 0; i < len-c; i++) {
					if(!sorting)
						break;
					if(list[i+1] < list[i]) {
						noswaps = false;
						swap(i,i+1);
					}	
					check = i+1;
					compare++;	acc+=2;
					Update();
					delay();
				}
				c++;
			}
		}
		
		public void oddEvenSort() {
			int c = 0;
			boolean noswaps = false;
			while(!noswaps && sorting) {
				noswaps = true;
				for(int i = 0+off; i < len-1; i+=2) {
					if(!sorting)
						break;
					if(list[i+1] < list[i]) {
						noswaps = false;
						swap(i,i+1);
					}
					current = i;
					check = i+1;
					compare++;	acc+=2;
					Update();
					delay();
				}
				off = 1 - off;
				c++;
			}
		}
		
		public void cocktailSort() {
			boolean noswaps = false;
			int c = 0;
			while(!noswaps && sorting) {
				noswaps = true;
				int i;
				int target;
				int inc;
				if(off == 1) {
					i = len-2-c;
					target = c-1;
					inc = -1;
				} else {
					i = c;
					target = len-2-c;
					inc = 1;
				}
				current = target+1;
				while(i!=target && sorting) {
					if(list[i] > list[i+1]) {
						noswaps = false;
						swap(i,i+1);
					}
					check = i+1-off;
					compare++;	acc+=2;
					Update();
					delay();
					i+=inc;
				}
				if(off == 1)
					c++;
				off = 1- off;
			}
		}
		
		public void heapSort() {
			heapify(len);
			int end = len-1;
			while(end > 0 && sorting) {
				current = end;
				swap(end,0);
				end--;
				siftDown(0,end);
				Update();
				delay();
			}
		}
		
		public void heapify(int n) {
			int start = iParent(n-1);
			while(start >= 0 && sorting) {
				siftDown(start, n-1);
				start--;
				Update();
				delay();
			}
		}
		
		public void siftDown(int start, int end) {
			int root = start;
			while(iLeftChild(root) <= end && sorting) {
				int child = iLeftChild(root);
				int swap = root;
				check = root;
				if(list[swap] < list[child]) {
					swap = child;
				} if(child+1 <= end && list[swap] < list[child+1]) {
					swap = child+1;
				} if(swap == root) {
					return;
				} else {
					swap(root,swap);
					check = root;
					root = swap;
				}
				compare+=3;	acc+=4;
				Update();
				delay();
			}
		}
		
		public int iParent(int i) { return ((i-1)/2); }
		public int iLeftChild(int i) { return 2*i + 1; }
		
		public void quickSort(int lo, int hi) {
			if(!sorting)
				return;
			current = hi;
			if(lo < hi) {
				int p = partition(lo,hi);
				quickSort(lo,p-1);
				quickSort(p+1, hi);
			}
		}
		
		public int partition(int lo, int hi) {
			int pivot = list[hi];	acc++;
			int i = lo - 1;
			for(int j = lo; j < hi; j++) {
				check = j;
				if(!sorting)
					break;
				if(list[j] < pivot) {
					i++;
					swap(i,j);	
				}
				compare++;	acc++;
				Update();
				delay();
			}
			swap(i+1,hi);
			Update();
			delay();
			return i+1;
		}
		
		void merge(int l, int m, int r)
	    {
	        int n1 = m - l + 1;
	        int n2 = r - m;
	 
	        int L[] = new int [n1];
	        int R[] = new int [n2];
	 
	        for (int i=0; i<n1; i++) {
	            L[i] = list[l + i];	acc++;
	        }
	        for (int j=0; j<n2; j++) {
	            R[j] = list[m + 1+ j];	acc++;
	        }
	        int i = 0, j = 0;

	        int k = l;
	        while (i < n1 && j < n2 && sorting) {
	        	check = k;
	            if (L[i] <= R[j]) {
	                list[k] = L[i];	acc++;
	                i++;
	            } else {
	                list[k] = R[j];	acc++;
	                j++;
	            }
	            compare++;
	            Update();
	            delay();
	            k++;
	        }

	        while (i < n1 && sorting) {
	            list[k] = L[i];	acc++;
	            i++;
	            k++;
	            Update();
	            delay();
	        }

	        while (j < n2 && sorting) {
	            list[k] = R[j];	acc++;
	            j++;
	            k++;
	            Update();
	            delay();
	        }
	    }

	    public void mergeSort(int l, int r) {
	        if (l < r) {
	            int m = (l+r)/2;
	            current = r;
	            mergeSort(l, m);
	            mergeSort(m+1, r);
	 
	            merge(l, m, r);
	        }
	    }
	    
	    public void pigeonholeSort() {
	    	int mi = 0;
	    	int size = len-mi+1;
	    	int[] holes = new int[size];
	    	for(int x:list)	{
	    		holes[x-mi] += 1;
	    	}
	    	int i = 0;
	    	for(int count = 0; count < size; count++) {
	    		while(holes[count] > 0 && sorting) {
	    			holes[count]--;
	    			check = i;
	    			list[i] = count+mi;	acc++;
	    			i++;
	    			Update();
	    			delay();
	    		}
	    	}
	    }
	    
	    public void radixSort(int n) {
	    	int m = getMax(n);
	    	 for(int exp = 1; m/exp > 0; exp *= 10) {
	    	 	if(!sorting)
	    	 		break;
	    		countSort(n,exp);
	    		Update();
	    		delay();
	    	 }
	    }
	    
	    public void countSort(int n, int exp) {
	    	int output[] = new int[n];
	    	int i;
	    	int count[] = new int[10];
	    	Arrays.fill(count, 0);
	    	
	    	for(i = 0; i < n; i++)	{
	    		count[(list[i]/exp)%10]++;	acc++;
	    	}
	    	
	    	for(i = 1; i < 10; i++) {
	    		count[i] += count[i - 1];
	    	}
	    	
	    	for(i = n -1; i >= 0; i--) {
	    		output[count[(list[i] / exp) % 10] - 1] = list[i];	acc++;
	    		count[(list[i] / exp) % 10]--;	acc++;
	    	}
	    	for(i = 0; i < n; i++) {
	    		if(!sorting)
	    			break;
	    		check = i;
	    		list[i] = output[i];	acc++;
	    		Update();
	    		delay();
	    	}
	    }
	    
	    public int getMax(int n) {
	    	int mx = list[0];
	    	for(int i = 1; i < n; i++) {
	    		if(list[i] > mx)
	    			mx = list[i];
	    		compare++;	acc++;
	    	}
	    	return mx;
	    }
	    
	    public void timSort(int n) {
	    	int RUN = 64;
	    	if(len >64) {
	    		while(((double)len/RUN)%2!=0)
	    			RUN--;
	    	}
	    	for(int i = 0; i < n; i+=RUN) {
	    		insertionSort(i, Math.min((i+RUN-1), (n-1)));
	    	}
	    	
	    	for(int size = RUN; size < n; size = 2*size) {
	    		for(int left = 0; left < n; left += 2*size) {
	    			int mid = left + size - 1;
	    			int right = Math.min((left + 2*size-1), (n-1));
	    			
	    			merge(left, mid, right);
	    		}
	    		if(!sorting)
					break;
	    	}
	    }
		
		public void bogoSort() {
			while(!checkSorted() && sorting) {
				for(int i = 0; i < len; i++) {
					if(!sorting)
						break;
					current = i;
					int rand = r.nextInt(len);
					check = rand;
					int temp = list[i];		acc++;
					list[i] = list[rand];	acc+=2;
					list[rand] = temp;		acc++;
					Update();
					delay();
				}
			}
		}
		
		public void swap(int i1, int i2) {
			int temp = list[i1];	acc++;
			list[i1] = list[i2];	acc+=2;
			list[i2] = temp;	acc++;
		}
		
		public boolean checkSorted() {
			for(int i = 0; i < len-1; i++) {
				if(list[i] > list[i+1]) {	acc+=2;
					return false;
				}
			}
			return true;
		}
	}
}
