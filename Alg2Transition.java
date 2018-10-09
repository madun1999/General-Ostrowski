static class Alg2Transition {
    // Will construct a transition for an automaton
    public int a = 0, b = 0, state = 0;
    
    public Alg2Transition(int a, int b, int state){
	setTransitions(a, b, state);
    }
    
    public void setTransitions(int a_, int b_, int state_){
	if (0<= a_ && a_<=2) a = a_;
	else a = -1; 
	if (0<= b_ && b_<=2) b = b_;
	else b = -1;
	if (0 <= state_ 0<= 81) state = state_;
	else state = -1;
    }
    
    public String toString(int a, int b, state){
	return Integer.toString(a) + Integer.toString(b) + " -> " + Integer.toString(state) + "\n";
    }
}
