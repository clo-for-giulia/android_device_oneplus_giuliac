package org.lineageos.settings.pwm.dc;

import com.android.settingslib.drawer.SwitchController;
import com.android.settingslib.drawer.SwitchesProvider;

import org.lineageos.settings.pwm.R;

import java.util.Collections;
import java.util.List;

public class DcDimmingSwitchesProvider extends SwitchesProvider {

    @Override
    protected List<SwitchController> createSwitchControllers() {
        return Collections.singletonList(new DcDimmingSwitchController());
    }

    private final class DcDimmingSwitchController extends SwitchController {

        @Override
        public String getSwitchKey() {
            return DcDimmingHelper.KEY_DC_DIMMING;
        }

        @Override
        protected MetaData getMetaData() {
            MetaData metaData = new MetaData(
                    "com.android.settings.category.ia.display"
            );
            metaData.setTitle(R.string.dc_dimming_enable_title);
            metaData.setSummary(R.string.dc_dimming_enable_summary);
            return metaData;
        }

        @Override
        public boolean isChecked() {
            return DcDimmingHelper.isEnabled(getContext());
        }

        @Override
        public boolean onCheckedChanged(boolean checked) {
            return DcDimmingHelper.setEnabled(getContext(), checked);
        }

        @Override
        protected String getErrorMessage(boolean attemptedChecked) {
            return null;
        }
    }
}
