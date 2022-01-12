
public abstract class Command {

	public final String displayText;
	
	public Command (String displayText){
		this.displayText = displayText;
		
	}
	
	abstract void execute ();
}
