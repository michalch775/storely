import styles from "../styles/ShortageTableCell.module.scss";
import React, {FC} from 'react';

const MediumWidget: React.FC<{children:any}>=({children, ...props})=> (
      <tr className={styles.main}>
        <td>Woda Cisowianka niegazowana</td>
        <td>42  /  50</td>
        <td>21</td>
        <td className={styles.barTd}>
          <div className={styles.flex}>
            <div>84%</div>
            <div className={styles.bar}>
              <div className={styles.barRed}
                style={{width:84}}/>
            </div>
          </div>
          </td>

      </tr>
  );


export default MediumWidget;
