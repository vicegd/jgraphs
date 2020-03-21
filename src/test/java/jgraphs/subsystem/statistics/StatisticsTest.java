package jgraphs.subsystem.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jgraphs.algorithm.backtracking.BacktrackingAll;
import jgraphs.app.permutation.PermutationModule;
import jgraphs.app.permutation.PermutationSituation;
import jgraphs.utils.Dependency;
import jgraphs.utils.module.EModuleConfiguration;

public class StatisticsTest {
    private BacktrackingAll backtracking;
    private TreeConsoleStatistics statistics;
        
    @Before
    public void initialize() {
        this.backtracking = Dependency.getInstance(new PermutationModule(EModuleConfiguration.BASIC)).getInjector(BacktrackingAll.class);
        this.statistics = new TreeConsoleStatistics();
        this.backtracking.addStatistics(this.statistics);
    }
        
    @Test
    public void givenInitState_whenGetAllPossiblePermutations1Elements() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(1));
        this.backtracking.run();        
        var info = this.statistics.getStatisticsInfo().get(this.statistics.getStatisticsInfo().size()-1); //the last one
        assertEquals(2, info.treeDepth);
        assertEquals(1, info.generatedTreeWidth);
        assertEquals(1, info.exploredTreeWidth);
        assertEquals(2, info.generatedNodes);
        assertEquals(2, info.exploredNodes);
        assertEquals(0, info.notExploredNodes);
        assertEquals(2, info.visits);
        assertEquals(1, info.visitsPerGeneratedNodes, 0.001);
        assertEquals(1, info.visitsPerExploredNodes, 0.001);
        assertEquals(2, info.topVisitedNodes.size());
        assertEquals(1, info.topRankedNodes.size());
    }

    @Test
    public void givenInitState_whenGetAllPossiblePermutations4Elements() {
    	var tree = this.backtracking.getStructure();
        tree.setFirstSituation(new PermutationSituation(4));
        this.backtracking.run();    
        var info = this.statistics.getStatisticsInfo().get(this.statistics.getStatisticsInfo().size()-1); //the last one
        assertEquals(5, info.treeDepth);
        assertEquals(24, info.generatedTreeWidth);
        assertEquals(24, info.exploredTreeWidth);
        assertEquals(65, info.generatedNodes);
        assertEquals(65, info.exploredNodes);
        assertEquals(0, info.notExploredNodes);
        assertEquals(65, info.visits);
        assertEquals(1, info.visitsPerGeneratedNodes, 0.001);
        assertEquals(1, info.visitsPerExploredNodes, 0.001);
        assertEquals(10, info.topVisitedNodes.size());
        assertEquals(1, info.topRankedNodes.size());
    }
}
