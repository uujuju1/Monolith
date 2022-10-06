package monolith.world.interfaces;

import monolith.world.PressureBlock.*;
import monolith.world.graph.*;
import monolith.world.modules.*;

public interface PressureInterface {
	default PressureModule getModule() {return null;}
	default PressureGraph getGraph() {return getModule().graph;}
	default PressureVertex getVertex() {return getModule().vertex;}

	default void addPressure(float value) {getModule().add(value);}
	default void subPressure(float value) {getModule().sub(value);}
	default void setPressure(float value) {getModule().set(value);}

	default boolean acceptPressure(PressureBuild src, float amount) {return true;}
	default boolean outputPressure(PressureBuild src, float amount) {return true;}

	// TODO make 2 methods?
	default void overflow() {}
} 