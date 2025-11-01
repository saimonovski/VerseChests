package io.github.saimonovski.versechest.paper.command.register;

import io.github.saimonovski.versechest.paper.VerseChest;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

public class CommandRegister {
    private final VerseChest plugin;

    public CommandRegister(VerseChest plugin) {
        this.plugin = plugin;
        final LegacyPaperCommandManager<CommandSender> manager = new LegacyPaperCommandManager<>(
                plugin,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        );
        manager.captionRegistry().registerProvider(MinecraftHelp.defaultCaptionsProvider());
        AnnotationParser<CommandSender> annotationParser = new AnnotationParser<>(manager, CommandSender.class);
        parseCommands(annotationParser);
    }

    private  void parseCommands(AnnotationParser<CommandSender> annotationParser) {
        annotationParser.parse(

        );
    }
}
