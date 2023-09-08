import { IExhibition } from 'app/shared/model/exhibition.model';

export interface IView {
  id?: number;
  voMember?: number | null;
  artwork?: IExhibition | null;
}

export const defaultValue: Readonly<IView> = {};
