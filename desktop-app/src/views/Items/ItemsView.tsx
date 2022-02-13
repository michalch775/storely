import styles from '../../styles/Items.module.scss';
import MediumWidget from "../../items/MediumWidget";
import SearchComponent from "../../components/Search/SearchComponent";
import SortSwitch from "../../components/SortSwitch";
import Filter from "../../components/Filter";
import React, {MutableRefObject, Ref, useCallback, useEffect, useRef, useState} from "react";
import {ItemsViewState} from "./ItemsViewState";
import {ItemsViewProps} from "./ItemsViewProps";
import {Item} from "../../api/entities/Item";
import InfiniteScroll from "react-infinite-scroller";

function ItemsView(props:ItemsViewProps):JSX.Element {

    const [state, setState] = useState<ItemsViewState>({
        offset: 0,
        text:"",
        isLoading:false
    });

    useEffect(()=>{
        console.log(props);
    })

    async function reload(value:string){
        if(!state.isLoading) {
            setState((prevState) => ({offset: 0, text: value, isLoading: true}));
            await props.onReload(value, state.offset);
            setState((prevState) => ({...prevState, isLoading: false}));
        }
    }

    async function loadMore(){
        if(!state.isLoading) {
            setState((prevState) => ({...prevState, offset: prevState.offset + 10, isLoading: true}));
            await props.onReload(state.text, state.offset);
            setState((prevState) => ({...prevState, isLoading: false}));
        }
    }


    function renderItem(item:Item, key:number){
        return(
            <tr className={styles.item } key={key}>
                <td>{item.itemTemplate.name}</td>
                <td>{item.itemTemplate.returnable ? null : item.quantity}</td>
                <td>21</td>
                <td>{item.itemTemplate.category.name}</td>
                <td>{item.itemTemplate.returnable ? "zwrotne" : "bezzwrotne"}</td>
            </tr>)
    }


  return (
      <div className={styles.main}>
          <div className={styles.widgets}>
              <MediumWidget number={254}>Wypożyczenia dziś</MediumWidget>
              <MediumWidget number={1024}>Pobrania dziś</MediumWidget>
              <MediumWidget number={5555}>Zwroty dziś</MediumWidget>
              <MediumWidget number={5555}>Nowych przedmiotów dziś</MediumWidget>
          </div>
          <div className={styles.list}>
              <div className={styles.listHeader}>
                  <h2>Przedmioty w magazynie</h2>
                  <div className={styles.listHeaderFlex}>
                      <SearchComponent onChange={reload}/>
                      <SortSwitch/>
                      <Filter/>
                  </div>
              </div>
              <InfiniteScroll
                  pageStart={0}
                  loadMore={loadMore}
                  hasMore={props.hasMore}
                  loader={<div className="loader" key={0}>Loading ...</div>}
                  useWindow={false}
              >
                  <table>
                      <thead>
                      <tr>
                          <th>Nazwa</th>
                          <th>Ilość</th>
                          <th>Średnia tygodniowa wydań</th>
                          <th>Kategoria</th>
                          <th>Rodzaj</th>
                      </tr>
                      </thead>
                      <tbody>

                          {props.items.map((item, key) => renderItem(item, key))}

                      </tbody>

                  </table>
              </InfiniteScroll>

          </div>
      </div>

  );
}

export default ItemsView;
