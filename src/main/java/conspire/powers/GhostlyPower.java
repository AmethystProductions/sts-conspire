package conspire.powers;

import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostlyPower extends AbstractConspirePower {
    public static final String POWER_ID = "conspire:Ghostly";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GhostlyPower(AbstractCreature owner) {
        super(POWER_ID, NAME, owner);
        this.amount = 1;
        this.updateDescription();
        this.priority = 99;
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05f);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && this.amount > 0) {
            this.flash();
            this.amount -= 1; // Don't use ReducePowerAction, we want to keep the power
            damageAmount = 1;
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.amount = 1;
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction((AbstractMonster)this.owner, "GHOSTLY"));
    }
}

