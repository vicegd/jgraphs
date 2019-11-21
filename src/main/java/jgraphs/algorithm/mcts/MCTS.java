package jgraphs.algorithm.mcts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import jgraphs.core.node.INode;
import jgraphs.core.tree.ITree;
import jgraphs.core.utils.BasicModule;
import jgraphs.core.utils.Utils;
import jgraphs.visualizers.IVisualizer;

public class MCTS {
	private static Logger log = LoggerFactory.getLogger(MCTS.class);
	private UCB ucb;
	private List<INode> moves;
	private long iterations;
	private long memory;
	private long seconds;
	private int winScore;
	private boolean trainP1;
	private boolean trainP2;
	private ITree tree;
	private List<IVisualizer> visualizers;
    
    public MCTS(ITree tree) {
    	this.tree = tree;
    	this.ucb = new UCB();
    	this.visualizers = new ArrayList<IVisualizer>();
    	this.moves = new ArrayList<INode>();
    	this.moves.add(tree.getRoot());
    	try (InputStream input = new FileInputStream("src/main/java/config.properties")) {
            var prop = new Properties();
            prop.load(input);
            this.iterations = Long.parseLong(prop.getProperty("mcts.budget_iterations"));
            this.memory = Long.parseLong(prop.getProperty("mcts.budget_memory"));
            this.seconds = Long.parseLong(prop.getProperty("mcts.budget_seconds"));
            this.winScore = Integer.parseInt(prop.getProperty("mcts.win_score"));
            this.trainP1 = Boolean.parseBoolean(prop.getProperty("mcts.train_p1"));
            this.trainP2 = Boolean.parseBoolean(prop.getProperty("mcts.train_p2"));
       } catch (IOException ex) {
       		log.error(ex.getMessage());
       }
    }
           
    public INode findNextMove(INode node) {     
    	var start = Instant.now();
    	for (var i = 1; i < Integer.MAX_VALUE; i++) {
            // Phase 1 - Selection
            var promisingNode = selection(node);
            
            // Phase 2 - Expansion
            if (promisingNode.getState().getBoard().checkStatus() == -1) { //-1 == IN PROGRESS
            	if ((i > 1) && (promisingNode.getState().getVisitCount() == 0)) {}
            	else 
            	expansion(promisingNode);
            }

            // Phase 3 - Simulation
            var nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            var result = simulation(nodeToExplore);
            // Phase 4 - Update
            var score = 0;
            if (this.trainP1) {
            	if (result == 1) score = this.winScore;
            	else if (result == 2) score = -this.winScore;
            }
            if (this.trainP2) {
            	if (result == 1) score = -this.winScore;
            	else if (result == 2) score = this.winScore;
            }
            backPropogation(nodeToExplore, score);
          //  System.out.println(i + " " + tree.getStatistics().numberNodes);
          //  System.out.println("Visits:" + promisingNode.getState().getVisitCount() + " WinScore:" + promisingNode.getState().getWinScore() + " ID:" + promisingNode.getId() + " PARENT:" + (promisingNode.getParent()!=null?promisingNode.getParent().getId():"null"));
            this.nodeSimulated(nodeToExplore);          
            this.treeChanged(node, nodeToExplore, result, i);
            
            if (checkStopCondition(i, start)) break; 
        }

        var winnerNode = node.getChildWithMaxValue();
        this.newMovement(winnerNode);
        this.moves.add(winnerNode);
        return winnerNode;
    }
    
    private boolean checkStopCondition(int i, Instant start) {
        var memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        
        if (memoryUsed >= this.memory) return true; //break because of memory
               
        var duration = Duration.between(start, Instant.now());       
        if (duration.getSeconds() >= this.seconds) return true; //break because of seconds
        
        if (i >= this.iterations) return true; //break because of iterations
        return false;
    }
    
    public void printPath() {
    	for (INode n : moves) {
    		log.info(n.toString());
    	}
    }
    
    public void setTrainP1(boolean train) {
    	this.trainP1 = train;
    }
    
    public void setTrainP2(boolean train) {
    	this.trainP2 = train;
    }

    public void setIterations(int iterations) {
    	this.iterations = iterations;
    }
    
    public void setWinScore(int winScore) {
    	this.setWinScore(winScore);
    }
    
    public void addVisualizer(IVisualizer visualizer) {
    	visualizers.add(visualizer);
    }
    
    private void nodeSimulated(INode node) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.nodeSimulated(node);
    	}
    }
    
    private void newMovement(INode node) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.newMovement(node);
    	}
    }
    
    private void treeChanged(INode currentNode, INode nodeToExplore, int result, int iteration) {
    	for(IVisualizer visualizer : visualizers) {
    		visualizer.treeChanged(this.tree, currentNode, nodeToExplore, result, this.moves.size(), iteration);
    	}
    }
    
    private INode selection(INode rootNode) {
        var node = rootNode;
       // System.out.println("ID:" + node.getId());
       // System.out.println("CHILDREN:" + node.getChildArray().size());
        for (INode n : node.getChildArray()) {
       //     System.out.println("\tCHILD:" + n.getId());
        }
        while (node.getChildArray().size() != 0) {
            node = ucb.findBestNode(node);
        //	System.out.println("\tID:" + node.getId());
        }
        return node;
    }

    private void expansion(INode promisingNode) {
    	var possibleStates = promisingNode.getState().getAllPossibleStates();
	    possibleStates.forEach(state -> {
	    	var newNode = Utils.getInstance().getInjector().getInstance(INode.class);
	    	newNode.setState(state);
	    	
	    	newNode.setParent(promisingNode);
	        promisingNode.getChildArray().add(newNode);       
	        tree.addNode(newNode);
	    });
    }

    private int simulation(INode node) {
        var tempNode = node.createNewNode();
        var tempState = tempNode.getState();
        var boardStatus = tempState.getBoard().checkStatus();

        while (boardStatus == -1) { //IN PROGRESS
            tempState.togglePlayer();
            tempState.randomPlay();
            boardStatus = tempState.getBoard().checkStatus();
        }

        return boardStatus;
    }
    
    private void backPropogation(INode nodeToExplore, int score) {
    	INode node = nodeToExplore;
        while (node != null) {
        	node.getState().incrementVisit();
            node.getState().addScore(score);
            node = node.getParent();         	
       }
    }
}
