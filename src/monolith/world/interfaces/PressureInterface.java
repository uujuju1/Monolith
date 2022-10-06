package monolith.world.interfaces;

import monolith.world.graph.*;
import monolith.world.modules.*;

public interface PressureInterface {
	default PressureModule getModule() {return null;}
	default PressureGraph getGraph() {return getModule().graph;}
	default PressureVertex getVertex() {return getModule().vertex;}

	default void addPressure(float value) {getModule().add(value)}
	default void subPressure(float value) {getModule().sub(value)}
	default void setPressure(float value) {getModule().set(value)}
}