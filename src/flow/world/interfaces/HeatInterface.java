package flow.world.interfaces;

import flow.world.graph.*;
import flow.world.modules.*;
import flow.world.blocks.HeatBlock.*;

public interface HeatInterface {
	default HeatModule getModule() {return null;}
	default HeatGraph getGraph() {return getModule().graph;}
	default HeatVertex getVertex() {return getModule().vertex;}

	default void addHeat(float value) {getModule().add(value);}
	default void subHeat(float value) {getModule().sub(value);}
	default void setHeat(float value) {getModule().set(value);}

	default boolean acceptHeat(HeatBuild src, float amount) {return true;}
	default boolean outputHeat(HeatBuild src, float amount) {return true;}

	default void overheat() {}
} 