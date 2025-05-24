//TODO to implement later
// 🧠 2. Refactor possible avec un State Pattern
// Actuellement on a une sorte de machine à états manuelle avec les booléens et le getChoice() + switch. 
// Une solution propre serait de faire une classe par état de menu (ex: MenuPrincipal, MenuCommandes, etc.) :
// PAR EXEMPLE le code si dessous

public abstract class MenuState {
    public abstract void handleEnter(Menu menu);
    public abstract void render(Menu menu);
}
