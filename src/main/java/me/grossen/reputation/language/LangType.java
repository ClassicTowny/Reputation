package me.grossen.reputation.language;

public enum LangType {

    PREFIX("prefix", "&bReputation &7»»"),
    NO_PERMISSION("no-permission", "&cYou do not have the right permission to do this."),
    COMMAND_ONLY_PLAYER("command.only-player", "&cYou can only run this command as a player."),
    COMMAND_HELP("command.help", "&6Reputation Help:\n&71. &6/reputation check <player> - &eCheck the reputation of a player.\n&72. &6/reputation set <player> <+/-> - &eSet your reputation of a player."),
    COMMAND_NO_ARGS("command.no-args", "&cNot enough arguments.\n&cCorrect usage: {command}"),
    COMMAND_CHECK_PLAYER_NOT_ONLINE("command.check.player-not-online", "&cCannot find this player. You can only check online players."),
    COMMAND_CHECK_SUCCESS("command.check.success", "&6{player}'s &ereputation is: &2[+] {good} &7/&c [-] {bad}"),
    COMMAND_SET_PLAYER_NOT_ONLINE("command.set.player-not-online", "&cCannot find this player. You can only set the reputation of online players."),
    COMMAND_SET_NOT_SELF("command.set.not.self", "&cYou cannot set your own reputation"),
    COMMAND_SET_INVALID_TYPE("command.set.invalid-type", "&cInvalid type of reputation. You must use +/-"),
    COMMAND_SET_SUCCESS_GOOD_NEW("command.set.success-good-new", "&eYour reputation of &6{player} &eset to &a[+]"),
    COMMAND_SET_SUCCESS_BAD_NEW("command.set.success-bad-new", "&eYou reputation of &6{player} &eset to &c[-]"),
    COMMAND_SET_SUCCESS_GOOD_ALREADY("command.set.success-good-already", "&eYour reputation of &6{player} &eis already &a[+]"),
    COMMAND_SET_SUCCESS_BAD_ALREADY("command.set.success-bad-already", "&eYour reputation of &6{player} &eis already &c[-]"),
    COMMAND_SET_SUCCESS_GOOD_CHANGE("command.set.success-good-change", "&eYour reputation of &6{player} &echanged to &a[+]"),
    COMMAND_SET_SUCCESS_BAD_CHANGE("command.set.success-bad-change", "&eYour reputation of &6{player} &echanged to &c[-]");

    private String path, def;

    LangType(String path, String def) {
        this.path = path;
        this.def = def;
    }

    public String getPath() {
        return path;
    }

    public String getDef() {
        return def;
    }

}
