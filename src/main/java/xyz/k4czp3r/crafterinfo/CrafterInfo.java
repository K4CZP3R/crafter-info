package xyz.k4czp3r.crafterinfo;

import io.papermc.lib.PaperLib;
//import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by Levi Muniz on 7/29/20.
 *
 * @author Copyright (c) Levi Muniz. All Rights Reserved.
 */
public class CrafterInfo extends JavaPlugin {

  @Override
  public void onEnable() {
    PaperLib.suggestPaper(this);

    saveDefaultConfig();
  }
}
