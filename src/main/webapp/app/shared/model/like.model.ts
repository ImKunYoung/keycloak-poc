import { IExhibition } from 'app/shared/model/exhibition.model';

export interface ILike {
  id?: number;
  voMember?: number | null;
  artwork?: IExhibition | null;
}

export const defaultValue: Readonly<ILike> = {};
