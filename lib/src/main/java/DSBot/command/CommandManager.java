package DSBot.command;

import java.util.List;

import com.google.common.base.Optional;

import DSBot.Library;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {
	
	public static final String PREFIX = "!";
	public static final String SCREEN = PREFIX + "screen";
	public static final String LADDER = PREFIX + "ladder";
	public static final String LINK = PREFIX + "link";
	public static final String UNLINK = PREFIX + "unlink";
	
	public static CommandRegistry commandRegistry = new CommandRegistry();
	private Library library;
	
	public CommandManager(Library library) { this.library = library; }
	
	static {
		commandRegistry.addCommand(new Command("screen", "Reconnaissance optique de caracteres sur un screen d'une interface de combat de defence d'un percepteur/prisme.", new CommandScreen(), "screen", "scr"));
		commandRegistry.addCommand(new Command("stats", "Affiche les statistiques de l'utilisateur.", new CommandStats(), "stats"));
		commandRegistry.addCommand(new Command("link", "Link un pseudo a l'identifiant du membre du serveur discord.", new CommandLink(), "link"));
		commandRegistry.addCommand(new Command("unlink", "Unlink un pseudo a l'identifiant du membre du serveur discord.", new CommandUnlink(), "unlink"));
		commandRegistry.addCommand(new Command("ladder", "Affiche le ladder d'un certain mois d'une certaine annee.", new CommandLadder(), "ladder"));
		commandRegistry.addCommand(new Command("mpall", "Envois un message privee aux membres des guildes mentionnees.", new CommandMpAll(), "mpall"));
		commandRegistry.addCommand(new Command("points", "Ajoute/Retire des points a un pseudo existant dans la base de donnees.", new CommandPoints(),"pts", "points"));
		commandRegistry.addCommand(new Command("help", "Ajoute/Retire des points � un pseudo existant dans la base de donnees.", new CommandHelp(),"hlp", "help"));
		commandRegistry.addCommand(new Command("pet", "Pet the frizouzou", new CommandPet(),"pet"));
	}
	
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().getId().equals("300644815996059648")) {
			Message message = event.getMessage();
			List<Emote> emote = message.getGuild().getEmotesByName("petthefrizouzou", false);
			if(!emote.isEmpty()) message.addReaction(emote.get(0)).queue();
		}
		String[] args = event.getMessage().getContentRaw().split(" ");
		if(args[0].length() < PREFIX.length()) return;
		String commandName = args[0].substring(PREFIX.length());
		Optional<Command> cmd = commandRegistry.getByAliases(commandName);
		if(cmd.isPresent()) {
			try { cmd.get().getCommandExecut().run(event, cmd.get(), library, args); }
			catch (Exception e) { e.printStackTrace(); }
		}
    }
}
